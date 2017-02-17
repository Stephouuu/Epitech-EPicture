package fr.epicture.epicture.interfaces;

/**
 * Created by Stephane on 16/02/2017.
 */

public interface AuthentificationInterface {

    void onUserPermissionRequest(String url, String grantWord);
    void onUserPermissionGranted();

}
