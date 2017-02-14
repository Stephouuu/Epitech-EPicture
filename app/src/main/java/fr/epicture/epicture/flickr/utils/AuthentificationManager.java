package fr.epicture.epicture.flickr.utils;

import android.content.Context;

import fr.epicture.epicture.flickr.requests.AuthRequestAsyncTask;

/**
 * Created by Stephane on 14/02/2017.
 */

public class AuthentificationManager {

    private Context context;
    private AuthRequestAsyncTask authRequestAsyncTask;

    public AuthentificationManager(Context context) {
        authRequestAsyncTask = null;
        this.context = context;
    }

    public void authentify() {
        if (!isAuthentificationRequestRunning()) {
            try {
                authRequestAsyncTask = new AuthRequestAsyncTask(context);
                authRequestAsyncTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isAuthentificationRequestRunning() {
        return (authRequestAsyncTask != null && !authRequestAsyncTask.isRunning());
    }




}
