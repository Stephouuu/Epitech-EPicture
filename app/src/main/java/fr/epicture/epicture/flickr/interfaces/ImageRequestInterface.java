package fr.epicture.epicture.flickr.interfaces;

import android.graphics.Bitmap;

import fr.epicture.epicture.flickr.utils.ImageElement;

/**
 * Created by Stephane on 15/02/2017.
 */

public interface ImageRequestInterface {

    void onFinish(ImageElement imageElement, Bitmap bitmap);
    void onError(int code);

}
