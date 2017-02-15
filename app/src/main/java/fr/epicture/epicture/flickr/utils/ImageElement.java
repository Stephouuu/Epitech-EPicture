package fr.epicture.epicture.flickr.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by Stephane on 15/02/2017.
 */

public class ImageElement {

    private static final String DIR_THUMBNAIL = "thumbnail";
    public static final String SIZE_THUMBNAIL = "t";

    public String id;
    public String owner;
    public String secret;
    public String server;
    public String farm;
    public String title;
    public boolean isPublic;
    public boolean isFriend;
    public boolean isFamily;

    public String size;


    public ImageElement(JSONObject jsonObject, String size) {
        try {
            id = jsonObject.getString("id");
            owner = jsonObject.getString("owner");
            secret = jsonObject.getString("secret");
            server = jsonObject.getString("server");
            farm = jsonObject.getString("farm");
            title = jsonObject.getString("title");
            isPublic = jsonObject.getInt("ispublic") == 1;
            isFriend = jsonObject.getInt("isfriend") == 1;
            isFamily = jsonObject.getInt("isfamily") == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.size = size;
    }

    public File getFile(@NonNull Context context) {
        File dir = context.getExternalCacheDir();
        File ret = null;
        if (dir != null) {
            String path = dir.getAbsolutePath();
            switch (size) {
                case SIZE_THUMBNAIL:
                    ret = new File(path + File.separator + DIR_THUMBNAIL, id + ".jpg");
                    break;
            }
        }
        return ret;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return object instanceof ImageElement
                && id.equals(((ImageElement) object).id)
                && size.equals(((ImageElement) object).size);
    }


    @Override
    public int hashCode() {
        return id.hashCode() + size.hashCode();
    }

}
