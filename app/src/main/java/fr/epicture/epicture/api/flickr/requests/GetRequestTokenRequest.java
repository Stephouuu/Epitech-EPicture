package fr.epicture.epicture.api.flickr.requests;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.epicture.epicture.api.flickr.FlickrClient;
import fr.epicture.epicture.api.flickr.interfaces.GetRequestTokenInterface;
import fr.epicture.epicture.api.flickr.modele.TokenRequest;
import fr.epicture.epicture.asynctasks.RequestAsyncTask;
import fr.epicture.epicture.utils.RequestIdentifierGenerator;
import fr.epicture.epicture.utils.StaticTools;

/**
 * Created by Stephane on 14/02/2017.
 */

public class GetRequestTokenRequest extends RequestAsyncTask {

    private static final String BASE_URL = "https://www.flickr.com/services";
    private static final String URL = "/oauth/request_token";

    //private String url;
    private GetRequestTokenInterface listener;

    public GetRequestTokenRequest(@NonNull Context context, /*String url,*/ GetRequestTokenInterface listener) {
        super(context);
        //this.url = url;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            String url = getURL();
            Log.i("url", url);
            GET(url);
            Log.i("GetRequestToken", response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        try {
            if (httpResponseCode == null || httpResponseCode == 200) {
                listener.onFinish(retrieveToken());
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
        String callback = "http://example.com";

        String[] params = new String[] {
                "oauth_nonce=" + random,
                "oauth_timestamp=" + unixTime,
                "oauth_consumer_key=" + FlickrClient.CONSUMER_KEY,
                "oauth_signature_method=HMAC-SHA1",
                "oauth_callback=" + callback
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
        String signature = StaticTools.OAuthEncode(StaticTools.getSignature(encoded, FlickrClient.CONSUMER_SECRET + "&"));

        return BASE_URL + URL + "?oauth_nonce=" + random
                + "&oauth_timestamp=" + unixTime
                + "&oauth_consumer_key=" + FlickrClient.CONSUMER_KEY
                + "&oauth_signature_method=HMAC-SHA1"
                + "&oauth_signature=" + signature
                + "&oauth_callback=" + callback;
    }

    private TokenRequest retrieveToken() throws Exception {
        TokenRequest tokenRequest = new TokenRequest();
        String[] datas = response.split("&");

        if (datas.length == 3) {
            tokenRequest.callbackConfirmed = datas[0].substring(datas[0].indexOf('=') + 1).equals("true");
            tokenRequest.token = datas[1].substring(datas[1].indexOf('=') + 1);
            tokenRequest.tokenSecret = datas[2].substring(datas[2].indexOf('=') + 1);
        }

        Log.i("token request", tokenRequest.callbackConfirmed + " " + tokenRequest.token + " " + tokenRequest.tokenSecret);

        return tokenRequest;
    }

}
