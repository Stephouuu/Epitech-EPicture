package fr.epicture.epicture.api.flickr.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.epicture.epicture.api.flickr.interfaces.BuddyIconRequestInterface;
import fr.epicture.epicture.api.flickr.interfaces.ImageDiskCacheInterface;
import fr.epicture.epicture.api.flickr.interfaces.ImageRequestInterface;
import fr.epicture.epicture.api.flickr.requests.BuddyIconRequest;
import fr.epicture.epicture.api.flickr.requests.ImageRequest;
import fr.epicture.epicture.utils.BitmapCache;

/**
 * Created by Stephane on 15/02/2017.
 */

public class ImageDiskCache {

    public final static String CACHE_TAG = "imagediskcache";

    private static final Map<ImageElement, WebImageRequestTask> WEBTASKS = new HashMap<>();
    private static final Map<ImageElement, WebBuddyIconRequestTask> WEBBUDDYTASKS = new HashMap<>();
    private static final Map<ImageElement, DiskTask> DISKTASKS = new HashMap<>();
    private static final Map<ImageElement, Set<ImageDiskCacheInterface>> LISTENERS = new HashMap<>();

    public void load(@NonNull final Context context, @NonNull final ImageElement element, @NonNull ImageDiskCacheInterface listener) {
        Bitmap bitmap = BitmapCache.getInCache(CACHE_TAG + element.id + element.size);
        if (bitmap != null && !bitmap.isRecycled()) {
            listener.onFinish(element, bitmap);
            return;
        }

        Set<ImageDiskCacheInterface> listeners = LISTENERS.get(element);
        if (listeners == null) {
            listeners = new HashSet<>();
        }
        listeners.add(listener);
        LISTENERS.put(element, listeners);
        WebImageRequestTask webtask = WEBTASKS.get(element);
        if (webtask != null) {
            return;
        }
        WebBuddyIconRequestTask webbuddytask = WEBBUDDYTASKS.get(element);
        if (webbuddytask != null) {
            return;
        }
        DiskTask disktask = DISKTASKS.get(element);
        if (disktask != null) {
            return;
        }
        File file = element.getFile(context);
        if (file != null && file.exists()) {
            disktask = new DiskTask(context, element, new ImageDiskCacheInterface() {
                @Override
                public void onFinish(@NonNull ImageElement element, @Nullable Bitmap bitmap) {
                    notifyResult(element, bitmap);
                }
            });
            DISKTASKS.put(element, disktask);
            disktask.execute();
        } else {
            if (element.type == ImageElement.TYPE_BUDDY) {
                webbuddytask = new WebBuddyIconRequestTask(context, element, new ImageDiskCacheInterface() {
                    @Override
                    public void onFinish(ImageElement element, Bitmap bitmap) {
                        notifyResult(element, bitmap);
                    }
                });
                WEBBUDDYTASKS.put(element, webbuddytask);
                webbuddytask.execute();
            } else {
                webtask = new WebImageRequestTask(context, element, new ImageDiskCacheInterface() {
                    @Override
                    public void onFinish(@NonNull ImageElement element, @Nullable Bitmap bitmap) {
                        notifyResult(element, bitmap);
                    }

                });
                WEBTASKS.put(element, webtask);
                webtask.execute();
            }
        }
    }

    private static void notifyResult(
            @NonNull ImageElement element,
            @Nullable Bitmap bitmap) {
        if (bitmap != null) {
            BitmapCache.putInCache(CACHE_TAG + element.id + element.size, bitmap);
        }
        WebImageRequestTask webtask = WEBTASKS.remove(element);
        if (webtask != null) {
            webtask.cancel(true);
        }
        WebBuddyIconRequestTask webbuddytask = WEBBUDDYTASKS.remove(element);
        if (webbuddytask != null) {
            webbuddytask.cancel(true);
        }
        DiskTask disktask = DISKTASKS.remove(element);
        if (disktask != null) {
            disktask.cancel(true);
        }
        Set<ImageDiskCacheInterface> listeners = LISTENERS.remove(element);
        if (listeners != null) {
            for (ImageDiskCacheInterface listener : listeners) {
                listener.onFinish(element, bitmap);
            }
        }
    }

    private static class WebImageRequestTask extends ImageRequest {
        WebImageRequestTask(@NonNull final Context context, @NonNull final ImageElement element, @NonNull final ImageDiskCacheInterface listener) {
            super(context, element, new ImageRequestInterface() {
                @Override
                public void onFinish(ImageElement imageElement, Bitmap bitmap) {
                    listener.onFinish(element, bitmap);
                }

                @Override
                public void onError(int code) {
                }
            });
        }
    }

    private static class WebBuddyIconRequestTask extends BuddyIconRequest {
        WebBuddyIconRequestTask(@NonNull final Context context, @NonNull final ImageElement element, @NonNull final ImageDiskCacheInterface listener) {
            super(context, element, new BuddyIconRequestInterface() {
                @Override
                public void onFinish(ImageElement imageElement, Bitmap bitmap) {
                    listener.onFinish(element, bitmap);
                }

                @Override
                public void onError(int code) {
                }
            });
        }
    }

    private static class DiskTask extends AsyncTask<Void, Void, Void> {

        private final Context context;
        private final ImageElement element;
        private final ImageDiskCacheInterface listener;
        private Bitmap bitmap;

        public DiskTask(@NonNull Context context, @NonNull ImageElement element, @NonNull ImageDiskCacheInterface listener) {
            this.context = context;
            this.element = element;
            this.listener = listener;
        }

        public void execute() {
            executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        @Override
        protected Void doInBackground(@Nullable Void... params) {
            File file = element.getFile(context);
            if (file != null) {
                String path = file.getAbsolutePath();
                bitmap = BitmapFactory.decodeFile(path);
            }
            return null;
        }

        @Override
        protected void onPostExecute(@Nullable Void result) {
            listener.onFinish(element, bitmap);
        }

    }

}
