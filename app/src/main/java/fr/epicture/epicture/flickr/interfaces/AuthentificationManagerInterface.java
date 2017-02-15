package fr.epicture.epicture.flickr.interfaces;

import fr.epicture.epicture.flickr.model.TokenAccess;
import fr.epicture.epicture.flickr.model.TokenRequest;

/**
 * Created by Stephane on 14/02/2017.
 */

public interface AuthentificationManagerInterface {

    void onRequestUserPermission(TokenRequest tokenRequest);
    void onAuthentificationFinished(TokenAccess tokenAccess);

}
