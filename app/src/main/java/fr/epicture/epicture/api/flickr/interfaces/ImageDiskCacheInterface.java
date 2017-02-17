package fr.epicture.epicture.api.flickr.interfaces;

import android.graphics.Bitmap;

import fr.epicture.epicture.api.flickr.utils.ImageElement;

/**
 * Created by Stephane on 15/02/2017.
 */

public interface ImageDiskCacheInterface {

    void onFinish(ImageElement imageElement, Bitmap bitmap);
}
