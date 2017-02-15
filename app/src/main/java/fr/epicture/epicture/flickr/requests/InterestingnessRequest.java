package fr.epicture.epicture.flickr.requests;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONObject;

import fr.epicture.epicture.flickr.asynctasks.RequestAsyncTask;
import fr.epicture.epicture.flickr.interfaces.InterestingnessRequestInterface;
import fr.epicture.epicture.flickr.model.TokenAccess;
import fr.epicture.epicture.flickr.utils.FlickrClient;

/**
 * Created by Stephane on 15/02/2017.
 */

/**
 * https://api.flickr.com/services/rest
 ?nojsoncallback=1 &oauth_nonce=84354935
 &format=json
 &oauth_consumer_key=653e7a6ecc1d528c516cc8f92cf98611
 &oauth_timestamp=1305583871
 &oauth_signature_method=HMAC-SHA1
 &oauth_version=1.0
 &oauth_token=72157626318069415-087bfc7b5816092c
 &oauth_signature=dh3pEH0Xk1qILr82HyhOsxRv1XA%3D
 &method=flickr.test.login
 */

public class InterestingnessRequest extends RequestAsyncTask {

    private static final String URL = "/rest";
    private static final String METHOD = "flickr.interestingness.getList";

    private TokenAccess tokenAccess;
    private int page;
    private InterestingnessRequestInterface listener;

    public InterestingnessRequest(@NonNull Context context, TokenAccess tokenAccess, int page, InterestingnessRequestInterface listener) {
        super(context);
        this.tokenAccess = tokenAccess;
        this.page = page;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            GET(getURL());
            Log.i("InterestingnessRequest", response);
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
                + "&page=" + page
                + "&api_key=" + FlickrClient.CONSUMER_KEY
                + "&method=" + METHOD
                + "&per_page=20";
    }

}
