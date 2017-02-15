package fr.epicture.epicture.flickr.utils;

import android.app.Activity;

import fr.epicture.epicture.flickr.interfaces.AuthentificationManagerInterface;
import fr.epicture.epicture.flickr.interfaces.GetAccessTokenInterface;
import fr.epicture.epicture.flickr.interfaces.GetRequestTokenInterface;
import fr.epicture.epicture.flickr.model.TokenAccess;
import fr.epicture.epicture.flickr.model.TokenRequest;
import fr.epicture.epicture.flickr.requests.GetAccessTokenRequest;
import fr.epicture.epicture.flickr.requests.GetRequestTokenRequest;

/**
 * Created by Stephane on 14/02/2017.
 */

public class AuthentificationManager {

    private Activity activity;
    private AuthentificationManagerInterface listener;

    private GetRequestTokenRequest getRequestTokenRequest;
    private GetAccessTokenRequest getAccessTokenRequest;

    public AuthentificationManager(Activity activity, AuthentificationManagerInterface listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public void authentify() {
        requestTokenRequest();
    }

    public void getAccessToken(TokenRequest tokenRequest) {
        requestTokenAccess(tokenRequest);
    }

    private boolean isGetRequestTokenRequestRunning() {
        return (getRequestTokenRequest != null && !getRequestTokenRequest.isRunning());
    }

    private void requestTokenRequest() {
        if (!isGetRequestTokenRequestRunning()) {
            getRequestTokenRequest = new GetRequestTokenRequest(activity, new GetRequestTokenInterface() {
                @Override
                public void onFinish(TokenRequest tokenRequest) {
                    requestUserPermission(tokenRequest);
                }

                @Override
                public void onError(int code) {
                }
            });
            getRequestTokenRequest.execute();
        }
    }

    private boolean isGetAccessTokenRequestRunning() {
        return (getAccessTokenRequest != null && !getAccessTokenRequest.isRunning());
    }

    private void requestTokenAccess(TokenRequest tokenRequest) {
        if (!isGetAccessTokenRequestRunning()) {
            getAccessTokenRequest = new GetAccessTokenRequest(activity, tokenRequest, new GetAccessTokenInterface() {
                @Override
                public void onFinish(TokenAccess tokenAccess) {
                    listener.onFinish(tokenAccess);
                }

                @Override
                public void onError(int code) {
                }
            });
            getAccessTokenRequest.execute();
        }
    }

    private void requestUserPermission(TokenRequest tokenRequest) {
        listener.onRequestUserPermission(tokenRequest);
    }

}
