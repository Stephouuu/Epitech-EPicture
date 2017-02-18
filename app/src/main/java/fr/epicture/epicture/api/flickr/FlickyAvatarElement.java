package fr.epicture.epicture.api.flickr;

import fr.epicture.epicture.api.APIImageElement;

/**
 * Created by Stephane on 18/02/2017.
 */

public class FlickyAvatarElement extends APIImageElement {

    private static final String URL_DEFAULT = "https://www.flickr.com/images/buddyicon.gif";
    private static final String URL = "https://farm%1$s.staticflickr.com/%2$s/buddyicons/%3$s.jpg";

    private String server;
    private String farm;

    public FlickyAvatarElement(String id, String server, String farm) {
        super(id, SIZE_THUMBNAIL);
        this.farm = farm;
        this.server = server;
    }

    @Override
    public String getURL() {
        String ret;

        if (server.equals("0")) {
            ret = URL_DEFAULT;
        } else {
            ret = String.format(URL, farm, server, getID());
        }
        return ret;
    }
}
