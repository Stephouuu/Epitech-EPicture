package fr.epicture.epicture.flickr.requests;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;

import fr.epicture.epicture.R;
import fr.epicture.epicture.flickr.asynctasks.RequestAsyncTask;
import fr.epicture.epicture.flickr.interfaces.ImageRequestInterface;
import fr.epicture.epicture.flickr.utils.ImageElement;
import fr.epicture.epicture.flickr.utils.StaticTools;

/**
 * Created by Stephane on 15/02/2017.
 */

public class ImageRequest extends RequestAsyncTask {

    private static final String URL = "https://farm%1$s.staticflickr.com/%2$s/%3$s_%4$s.jpg";

    private ImageElement element;
    private ImageRequestInterface listener;

    private Bitmap bitmap;

    public ImageRequest(@NonNull Context context, ImageElement element, ImageRequestInterface listener) {
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
        return String.format(URL, element.farm, element.server, element.id, element.secret);
    }

    private void makeBitmap () {
        File file = element.getFile(getContext());
        if (file != null && image != null) {
            try {
                switch (element.size) {
                    case ImageElement.SIZE_THUMBNAIL:
                        int size = getContext().getResources().getDimensionPixelSize(R.dimen.image_size_thumbnail);
                        bitmap = Bitmap.createScaledBitmap(image, size, size, true);
                        StaticTools.saveBitmapToJpegFile(bitmap, file);
                        image.recycle();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
