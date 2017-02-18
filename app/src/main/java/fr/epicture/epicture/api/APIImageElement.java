package fr.epicture.epicture.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * Created by Stephane on 18/02/2017.
 */

public abstract class APIImageElement {

    private static final String DIR_THUMBNAIL = "thumbnail";
    private static final String DIR_PREVIEW = "preview";

    public static final int SIZE_THUMBNAIL = 1;
    public static final int SIZE_PREVIEW = 2;

    //public static final String SIZE_THUMBNAIL = "t";
    //public static final String SIZE_PREVIEW = "z";

    private String id;
    private int size;

    public APIImageElement() {
        this.id = "undefined";
        this.size = 0;
    }

    public APIImageElement(String id, int size) {
        this.id = id;
        this.size = size;
    }

    public String getID() {
        return id;
    }

    public int getSize() {
        return size;
    }

    protected void setID(String id) {
        this.id = id;
    }

    protected void setSize(int size) {
        this.size = size;
    }

    public File getFile(@NonNull Context context) {
        File dir = context.getExternalCacheDir();
        String path = getFilePath(context);
        if (!path.isEmpty()) {
            return new File(getFilePath(context));
        }
        return null;
    }

    public String getFilePath(@NonNull Context context) {
        String ret = "";
        File dir = context.getExternalCacheDir();
        if (dir != null) {
            String path = dir.getAbsolutePath();
            switch (size) {
                case SIZE_THUMBNAIL:
                    ret = path + File.separator + DIR_THUMBNAIL + id + ".jpg";
                    break;
                case SIZE_PREVIEW:
                    ret = path + File.separator + DIR_PREVIEW + id + ".jpg";
                    break;
            }
        }
        return ret;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return object instanceof APIImageElement
                && id.equals(((APIImageElement) object).getID())
                && size == ((APIImageElement) object).getSize();
    }


    @Override
    public int hashCode() {
        return id.hashCode() + size;
    }

    public abstract String getURL();

}
