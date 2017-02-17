package fr.epicture.epicture.api.flickr.utils;

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
    private static final String DIR_PREVIEW = "preview";

    public static final String SIZE_THUMBNAIL = "t";
    public static final String SIZE_PREVIEW = "z";

    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_BUDDY = 2;

    public String id;
    public String owner;
    public String secret;
    public String server;
    public String farm;
    public String title;
    public boolean isPublic;
    public boolean isFriend;
    public boolean isFamily;

    public int type;
    public String size;

    public ImageElement(JSONObject jsonObject, int type, String size) {
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
        this.type = type;
        this.size = size;
    }

    public ImageElement(String farm, String server, String nsid) {
        this.id = nsid;
        this.farm = farm;
        this.server = server;
        this.type = TYPE_BUDDY;
        this.size = SIZE_THUMBNAIL;
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
        return object instanceof ImageElement
                && id.equals(((ImageElement) object).id)
                && size.equals(((ImageElement) object).size);
    }


    @Override
    public int hashCode() {
        return id.hashCode() + size.hashCode();
    }

}
