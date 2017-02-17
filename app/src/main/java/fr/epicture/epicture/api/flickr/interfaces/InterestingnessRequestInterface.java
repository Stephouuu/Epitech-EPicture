package fr.epicture.epicture.api.flickr.interfaces;

import org.json.JSONObject;

/**
 * Created by Stephane on 15/02/2017.
 */

public interface InterestingnessRequestInterface {

    void onFinish(JSONObject jsonObject);
    void onError(int responseCode);

}
