package fr.epicture.epicture.api.imgur.requests;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.File;

import fr.epicture.epicture.api.APIImageElement;
import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.requests.UploadRequest;
import fr.epicture.epicture.utils.BitmapCache;
import fr.epicture.epicture.utils.ImageDiskCache;

/**
 * Created by Stephane on 26/02/2017.
 */

public class UploadImageRequest extends UploadRequest {

    private static final String URL = "https://api.imgur.com/3/upload";

    private APIImageElement element;
    private String accessToken;

    public UploadImageRequest(@NonNull Context context, APIImageElement element, String accessToken, LoadTextInterface listener) {
        super(context, URL, listener);

        this.element = element;
        this.accessToken = accessToken;

        addHeader();
        addParam();

        execute();
    }

    private void addHeader() {
        try {
            addHeader("Authorization", "Bearer " + accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addParam() {
        if (element.title != null && !element.title.isEmpty()) {
            addParam(new ParamBody("title", element.title));
        }
        if (element.description != null && !element.description.isEmpty()) {
            addParam(new ParamBody("description", element.description));
        }

        Bitmap bitmap = BitmapCache.getInCache(ImageDiskCache.CACHE_TAG + element.getID() + element.getSize());
        byte[] bytes;
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            bytes = stream.toByteArray();
        } else {
            bytes = this.createByteArray(element.path);
        }
        addParam(new ParamFile("img.jpg", "image", bytes, bytes.length, "image/jpeg"));
    }

    private byte[] createByteArray(String path) {
        File file = new File(path);

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        float height = (float) bitmap.getHeight();
        float width = (float) bitmap.getWidth();
        float ratio = height / width;

        if (ratio > 1) {
            if (bitmap.getHeight() > 640) {
                height = 640.0f;
                width = 640.0f / ratio;
            }
        } else {
            if (bitmap.getWidth() > 640) {
                width = 640.0f;
                height = 640.0f * ratio;
            }
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) width, (int) height, true);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();

        return byteArray;
    }

}
