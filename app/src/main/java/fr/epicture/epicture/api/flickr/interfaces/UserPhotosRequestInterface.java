package fr.epicture.epicture.api.flickr.interfaces;

import org.json.JSONObject;

/**
 * Created by Stephane on 16/02/2017.
 */

public interface UserPhotosRequestInterface {

    void onFinish(JSONObject jsonObject);
    void onError(int code);

}
