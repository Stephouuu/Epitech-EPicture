package fr.epicture.epicture.api.flickr.interfaces;

import fr.epicture.epicture.api.flickr.modele.TokenRequest;

/**
 * Created by Stephane on 14/02/2017.
 */

public interface GetRequestTokenInterface {

    void onFinish(TokenRequest tokenRequest);
    //void onFinish(String response);
    void onError(int code);

}
