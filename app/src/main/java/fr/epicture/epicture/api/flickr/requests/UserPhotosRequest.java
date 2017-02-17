package fr.epicture.epicture.api.flickr.requests;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.epicture.epicture.api.flickr.FlickrClient;
import fr.epicture.epicture.api.flickr.interfaces.UserPhotosRequestInterface;
import fr.epicture.epicture.asynctasks.RequestAsyncTask;
import fr.epicture.epicture.utils.RequestIdentifierGenerator;
import fr.epicture.epicture.utils.StaticTools;

/**
 * Created by Stephane on 16/02/2017.
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

public class UserPhotosRequest extends RequestAsyncTask {

    private static final String BASE_URL = "https://www.flickr.com/services";
    private static final String URL = "/rest/";
    private static final String METHOD = "flickr.people.getPhotos";

    private int page;
    private String userID;
    private UserPhotosRequestInterface listener;

    public UserPhotosRequest(@NonNull Context context, int page, String userID, UserPhotosRequestInterface listener) {
        super(context);
        this.page = page;
        this.userID = userID;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            GET(getURL());
            Log.i("UserPhotosRequest", response);
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
        String part1 = "GET";
        String part2 = BASE_URL + URL;

        String random = RequestIdentifierGenerator.Generate();
        long unixTime = StaticTools.GetCurrentUnixTime();

        String[] params = new String[] {
                "nojsoncallback=1",
                "oauth_nonce=" + random,
                "format=json",
                "oauth_consumer_key=" + FlickrClient.CONSUMER_KEY,
                "oauth_timestamp=" + unixTime,
                "oauth_signature_method=HMAC-SHA1",
                //"oauth_token=" + FlickrClient.tokenAccess.token,
                "method=" + METHOD,
                "user_id=" + userID
        };
        List<String> encodedParams = new ArrayList<>();

        Arrays.sort(params);

        for (int i = 0 ; i < params.length ; i++) {
            String name = params[i].substring(0, params[i].indexOf('='));
            String data = params[i].substring(params[i].indexOf('=') + 1);
            String encoded = StaticTools.OAuthEncode(name + "=") + StaticTools.OAuthEncode(StaticTools.OAuthEncode(data));
            if (i < params.length - 1) {
                encoded += StaticTools.OAuthEncode("&");
            }
            encodedParams.add(encoded);
        }

        String part1Encoded = StaticTools.OAuthEncode(part1);
        String part2Encoded = StaticTools.OAuthEncode(part2);
        String part3Encoded = TextUtils.join("", encodedParams);
        String encoded = part1Encoded + "&" + part2Encoded + "&" + part3Encoded;
        //String signature = StaticTools.OAuthEncode(StaticTools.getSignature(encoded, FlickrClient.CONSUMER_SECRET + "&" + FlickrClient.tokenAccess.tokenSecret));

        return BASE_URL + URL + "?nojsoncallback=1"
                + "&oauth_nonce=" + random
                + "&format=json"
                + "&oauth_consumer_key=" + FlickrClient.CONSUMER_KEY
                + "&oauth_timestamp=" + unixTime
                + "&oauth_signature_method=HMAC-SHA1"
                //+ "&oauth_token=" + FlickrClient.tokenAccess.token
                //+ "&oauth_signature=" + signature
                + "&method=" + METHOD
                + "&user_id=" + userID;
    }
}
