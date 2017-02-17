package fr.epicture.epicture.api.flickr.requests;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;

import fr.epicture.epicture.R;
import fr.epicture.epicture.api.flickr.interfaces.BuddyIconRequestInterface;
import fr.epicture.epicture.api.flickr.utils.ImageElement;
import fr.epicture.epicture.asynctasks.RequestAsyncTask;
import fr.epicture.epicture.utils.StaticTools;

/**
 * Created by Stephane on 15/02/2017.
 */

public class BuddyIconRequest extends RequestAsyncTask {

    private static final String URL = "https://farm%1$s.staticflickr.com/%2$s/buddyicons/%3$s.jpg";
    private static final String URL_DEFAULT = "https://www.flickr.com/images/buddyicon.gif";

    private ImageElement element;
    private BuddyIconRequestInterface listener;
    private Bitmap bitmap;

    public BuddyIconRequest(@NonNull Context context, ImageElement element, BuddyIconRequestInterface listener) {
        super(context);
        this.element = element;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            String url = getURL();
            Log.i("imageRequest", "url: " + url);

            GETImage(url);

            makeBitmap();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        try {
            if (httpResponseCode == 200) {
                listener.onFinish(element, bitmap);
            } else {
                listener.onError(httpResponseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getURL() throws Exception {
        String url;

        if (!element.server.equals("0")) {
            url = String.format(URL, element.farm, element.server, element.id);
        }
        else {
            url = URL_DEFAULT;
        }
        return url;
    }

    private void makeBitmap () {
        File file = element.getFile(getContext());
        if (file != null && image != null) {
            try {
                int size = getContext().getResources().getDimensionPixelSize(R.dimen.image_size_thumbnail);
                bitmap = Bitmap.createScaledBitmap(image, size, size, true);
                StaticTools.saveBitmapToJpegFile(bitmap, file);
                image.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
