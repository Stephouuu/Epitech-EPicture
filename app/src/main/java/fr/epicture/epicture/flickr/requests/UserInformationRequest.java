package fr.epicture.epicture.flickr.requests;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONObject;

import fr.epicture.epicture.flickr.asynctasks.RequestAsyncTask;
import fr.epicture.epicture.flickr.interfaces.UserInformationInterface;
import fr.epicture.epicture.flickr.utils.FlickrClient;

/**
 * Created by Stephane on 15/02/2017.
 */

public class UserInformationRequest extends RequestAsyncTask {

    private static final String URL = "/rest";
    private static final String METHOD = "flickr.people.getInfo";

    private String userID;
    private UserInformationInterface listener;

    public UserInformationRequest(@NonNull Context context, String userID, UserInformationInterface listener) {
        super(context);
        this.userID = userID;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            String url = getURL();

            GET(url);

            Log.i("UserInfoRequest", "GET " + url);
            Log.i("UserInfoRequest", "BODY " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        try {
            if (httpResponseCode == 200) {
                listener.onFinish(new JSONObject(response));
            } else {
                listener.onError(httpResponseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getURL() throws Exception {
        return BASE_URL + URL + "?nojsoncallback=1"
                + "&format=json"
                + "&api_key=" + FlickrClient.CONSUMER_KEY
                + "&user_id=" + userID
                + "&method=" + METHOD;
    }
}
