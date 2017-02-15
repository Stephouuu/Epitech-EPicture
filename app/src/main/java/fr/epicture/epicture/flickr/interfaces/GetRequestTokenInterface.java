package fr.epicture.epicture.flickr.interfaces;

import fr.epicture.epicture.flickr.model.TokenRequest;

/**
 * Created by Stephane on 14/02/2017.
 */

public interface GetRequestTokenInterface {

    void onFinish(TokenRequest tokenRequest);
    void onError(int code);

}
