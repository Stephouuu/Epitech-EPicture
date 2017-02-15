package fr.epicture.epicture.flickr.interfaces;

import fr.epicture.epicture.flickr.model.TokenAccess;

/**
 * Created by Stephane on 15/02/2017.
 */

public interface GetAccessTokenInterface {

    void onFinish(TokenAccess tokenAccess);
    void onError(int code);

}
