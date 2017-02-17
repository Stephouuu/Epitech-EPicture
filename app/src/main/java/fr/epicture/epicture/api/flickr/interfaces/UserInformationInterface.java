package fr.epicture.epicture.api.flickr.interfaces;

import org.json.JSONObject;

/**
 * Created by Stephane on 15/02/2017.
 */

public interface UserInformationInterface {

    void onFinish(JSONObject jsonObject);
    void onError(int code);

}
