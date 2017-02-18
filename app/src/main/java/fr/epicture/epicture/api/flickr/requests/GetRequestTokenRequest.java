package fr.epicture.epicture.api.flickr.requests;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.epicture.epicture.api.flickr.FlickrClient;
import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.requests.TextRequest;
import fr.epicture.epicture.utils.RequestIdentifierGenerator;
import fr.epicture.epicture.utils.StaticTools;

/**
 * Created by Stephane on 14/02/2017.
 */

public class GetRequestTokenRequest extends TextRequest {

    private static final String URL = "https://www.flickr.com/services/oauth/request_token";

    public GetRequestTokenRequest(@NonNull Context context, LoadTextInterface listener) {
        super(context, listener);

        try {
            setUrl(getURL());
            execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getURL() throws Exception {
        String part1 = "GET";
        String part2 = URL;

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

        return URL + "?oauth_nonce=" + random
                + "&oauth_timestamp=" + unixTime
                + "&oauth_consumer_key=" + FlickrClient.CONSUMER_KEY
                + "&oauth_signature_method=HMAC-SHA1"
                + "&oauth_signature=" + signature
                + "&oauth_callback=" + callback;
    }

}
