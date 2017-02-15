package fr.epicture.epicture.flickr.requests;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.epicture.epicture.flickr.asynctasks.RequestAsyncTask;
import fr.epicture.epicture.flickr.interfaces.GetAccessTokenInterface;
import fr.epicture.epicture.flickr.model.TokenAccess;
import fr.epicture.epicture.flickr.model.TokenRequest;
import fr.epicture.epicture.flickr.utils.FlickrClient;
import fr.epicture.epicture.flickr.utils.RequestIdentifierGenerator;
import fr.epicture.epicture.flickr.utils.StaticTools;

/**
 * Created by Stephane on 15/02/2017.
 */

public class GetAccessTokenRequest extends RequestAsyncTask {

    private static final String URL = "/oauth/access_token";

    private TokenRequest tokenRequest;
    private GetAccessTokenInterface listener;

    public GetAccessTokenRequest(@NonNull Context context, TokenRequest tokenRequest, GetAccessTokenInterface listener) {
        super(context);
        this.tokenRequest = tokenRequest;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            GET(getURL());
            Log.i("GetAccessToken", response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        try {
            if (httpResponseCode == 200) {
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

        String[] params = new String[] {
                "oauth_nonce=" + random,
                "oauth_timestamp=" + unixTime,
                "oauth_verifier=" + tokenRequest.verifier,
                "oauth_consumer_key=" + FlickrClient.CONSUMER_KEY,
                "oauth_signature_method=HMAC-SHA1",
                "oauth_token=" + tokenRequest.token
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
        String signature = StaticTools.OAuthEncode(StaticTools.getSignature(encoded, FlickrClient.CONSUMER_SECRET + "&" + tokenRequest.tokenSecret));

        return BASE_URL + URL + "?oauth_nonce=" + random
                + "&oauth_timestamp=" + unixTime
                + "&oauth_verifier=" + tokenRequest.verifier
                + "&oauth_consumer_key=" + FlickrClient.CONSUMER_KEY
                + "&oauth_signature_method=HMAC-SHA1"
                + "&oauth_token=" + tokenRequest.token
                + "&oauth_signature=" + signature;
    }

    private TokenAccess retrieveToken() throws Exception {
        TokenAccess tokenAccess = new TokenAccess();
        String[] datas = response.split("&");
        if (datas.length == 5) {
            tokenAccess.fullname = URLDecoder.decode(datas[0].substring(datas[0].indexOf('=') + 1), "UTF-8");
            tokenAccess.token = datas[1].substring(datas[1].indexOf('=') + 1);
            tokenAccess.tokenSecret = datas[2].substring(datas[2].indexOf('=') + 1);
            tokenAccess.nsid = datas[3].substring(datas[3].indexOf('=') + 1);
            tokenAccess.username = URLDecoder.decode(datas[4].substring(datas[4].indexOf('=') + 1), "UTF-8");
        }
        return tokenAccess;
    }

}
