package fr.epicture.epicture.flickr.requests;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.apache.commons.codec.binary.Base64;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import fr.epicture.epicture.flickr.utils.FlickrClient;
import fr.epicture.epicture.flickr.utils.RequestAsyncTask;
import fr.epicture.epicture.flickr.utils.RequestIdentifierGenerator;
import fr.epicture.epicture.flickr.utils.StaticTools;

/**
 * Created by Stephane on 14/02/2017.
 */

public class AuthRequestAsyncTask extends RequestAsyncTask {

    public static final String BASE_URL = "https://www.flickr.com/services/oauth/request_token";

    public AuthRequestAsyncTask(@NonNull Context context) {
        super(context);
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            GET(getURL());

            if (json != null) {
                Log.i("auth response", json.toString());
            } else if (jsonError != null) {
                Log.i("auth response error", jsonError.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

    }

    @Override
    protected void onPreExecute() {

    }

    private String getURL() throws Exception {
        String part1 = "GET";
        String part2 = BASE_URL;

        String random = RequestIdentifierGenerator.Generate();
        long unixTime = StaticTools.GetCurrentUnixTime();

        String[] params = new String[] {
                "oauth_nonce=" + random,
                "oauth_timestamp=" + unixTime,
                "oauth_consumer_key=" + FlickrClient.CONSUMER_KEY,
                "oauth_signature_method=HMAC-SHA1",
                "oauth_callback=http://www.example.com"
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
        String signature = StaticTools.OAuthEncode(getSignature(encoded, FlickrClient.CONSUMER_SECRET + "&"));

        return "https://www.flickr.com/services/oauth/request_token"
                + "?oauth_nonce=" + random
                + "&oauth_timestamp=" + unixTime
                + "&oauth_consumer_key=" + FlickrClient.CONSUMER_KEY
                + "&oauth_signature_method=HMAC-SHA1"
                + "&oauth_signature=" + signature
                + "&oauth_callback=http://www.example.com";
    }

    private String getSignature(String data, String key) throws Exception {
        final String HMAC_ALGORITHM = "HmacSHA1";
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), HMAC_ALGORITHM);
        Mac macInstance = Mac.getInstance(HMAC_ALGORITHM);
        macInstance.init(keySpec);
        byte[] signedBytes = macInstance.doFinal(data.getBytes());
        return (new String(Base64.encodeBase64(signedBytes)));
    }
}
