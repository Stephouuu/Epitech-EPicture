package fr.epicture.epicture.api.flickr;

import org.json.JSONObject;

import fr.epicture.epicture.api.APIImageElement;

/**
 * Created by Stephane on 15/02/2017.
 */

public class FlickrImageElement extends APIImageElement {

    private static final String URL = "https://farm%1$s.staticflickr.com/%2$s/%3$s_%4$s%5$s.jpg";

    public String owner;
    public String secret;
    public String server;
    public String farm;
    public boolean isPublic;
    public boolean isFriend;
    public boolean isFamily;

    public FlickrImageElement(JSONObject jsonObject, int size) {
        try {
            setID(jsonObject.getString("id"));
            setSize(size);
            owner = jsonObject.getString("owner");
            secret = jsonObject.getString("secret");
            server = jsonObject.getString("server");
            farm = jsonObject.getString("farm");
            title = jsonObject.getString("title");
            isPublic = jsonObject.getInt("ispublic") != 0;
            isFriend = jsonObject.getInt("isfriend") != 0;
            isFamily = jsonObject.getInt("isfamily") != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FlickrImageElement(String path, String title, String description) {
        super(path, title, description);
    }

    @Override
    public String getURL() {
        String strSize = "";
        if (getSize() == SIZE_THUMBNAIL) {
            strSize = "_t";
        }
        else if (getSize() == SIZE_PREVIEW) {
            strSize = "_z";
        }
        return String.format(URL, farm, server, getID(), secret, strSize);
    }

}
