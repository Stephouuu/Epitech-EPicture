package fr.epicture.epicture.interfaces;

import android.graphics.Bitmap;

import fr.epicture.epicture.api.APIImageElement;

/**
 * Created by Stephane on 18/02/2017.
 */

public interface ImageDiskCacheInterface {

    void onFinish(APIImageElement element, Bitmap bitmap);

}
