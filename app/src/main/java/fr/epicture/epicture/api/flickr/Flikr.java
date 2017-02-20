package fr.epicture.epicture.api.flickr;

import android.content.Context;

import java.util.Collection;
import java.util.Collections;

import fr.epicture.epicture.api.API;
import fr.epicture.epicture.api.APIAccount;
import fr.epicture.epicture.interfaces.AuthentificationInterface;
import fr.epicture.epicture.interfaces.GetRequestTokenInterface;
import fr.epicture.epicture.model.TokenAccess;
import fr.epicture.epicture.model.TokenRequest;
import fr.epicture.epicture.requests.GetAccessTokenRequest;
import fr.epicture.epicture.requests.GetRequestTokenRequest;

public class Flikr implements API {

    public static final String CONSUMER_KEY = "dfe1258dc806ea2d25a37c4937493dee";
    public static final String CONSUMER_SECRET  = "b74812b20513a78d";

    private TokenRequest tokenRequest;
    private TokenAccess tokenAccess;

    private static final String authorizeLink = "https://www.flickr.com/services/oauth/authorize?oauth_token=%s&perms=delete";
    private static final String name = "Flikr";

    private static final String baseTokenRequestURL = "https://www.flickr.com/services/oauth/request_token";
    private static final String baseTokenAccessURL = "https://www.flickr.com/services/oauth/access_token";


    /*@Override
    public String getTokenRequestLink() {
        try {
            String part1 = "GET";
            String part2 = baseTokenRequestURL;

            String random = RequestIdentifierGenerator.Generate();
            long unixTime = StaticTools.GetCurrentUnixTime();
            String callback = "http://example.com";

            String[] params = new String[] {
                    "oauth_nonce=" + random,
                    "oauth_timestamp=" + unixTime,
                    "oauth_consumer_key=" + CONSUMER_KEY,
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
            String signature = StaticTools.OAuthEncode(StaticTools.getSignature(encoded, CONSUMER_SECRET + "&"));

            return baseTokenRequestURL + "?oauth_nonce=" + random
                    + "&oauth_timestamp=" + unixTime
                    + "&oauth_consumer_key=" + CONSUMER_KEY
                    + "&oauth_signature_method=HMAC-SHA1"
                    + "&oauth_signature=" + signature
                    + "&oauth_callback=" + callback;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String getUserAuthorizationGrantWord() {
        return "oauth_verifier";
    }

    @Override
    public String getUserAuthorizationLink(String response) {
        retrieveToken(response);
        return String.format(authorizeLink, tokenRequest.token);
    }

    @Override
    public String getTokenAccessLink(String response) {
        try {
            tokenRequest.verifier = Uri.parse(response).getQueryParameter("oauth_verifier");
            Log.i("access", "verifier: " + tokenRequest.verifier);

            String part1 = "GET";
            String part2 = baseTokenAccessURL;

            String random = RequestIdentifierGenerator.Generate();
            long unixTime = StaticTools.GetCurrentUnixTime();

            String[] params = new String[]{
                    "oauth_nonce=" + random,
                    "oauth_timestamp=" + unixTime,
                    "oauth_verifier=" + tokenRequest.verifier,
                    "oauth_consumer_key=" + FlickrClient.CONSUMER_KEY,
                    "oauth_signature_method=HMAC-SHA1",
                    "oauth_token=" + tokenRequest.token
            };
            List<String> encodedParams = new ArrayList<>();

            Arrays.sort(params);

            for (int i = 0; i < params.length; i++) {
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
            String signature = StaticTools.OAuthEncode(StaticTools.getSignature(encoded, CONSUMER_SECRET + "&" + tokenRequest.tokenSecret));

            return baseTokenAccessURL + "?oauth_nonce=" + random
                    + "&oauth_timestamp=" + unixTime
                    + "&oauth_verifier=" + tokenRequest.verifier
                    + "&oauth_consumer_key=" + FlickrClient.CONSUMER_KEY
                    + "&oauth_signature_method=HMAC-SHA1"
                    + "&oauth_token=" + tokenRequest.token
                    + "&oauth_signature=" + signature;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void setAccessToken(String response) {
        try {
            TokenAccess tokenAccess = new TokenAccess();
            String[] datas = response.split("&");
            if (datas.length == 5) {
                tokenAccess.fullname = URLDecoder.decode(datas[0].substring(datas[0].indexOf('=') + 1), "UTF-8");
                tokenAccess.token = datas[1].substring(datas[1].indexOf('=') + 1);
                tokenAccess.tokenSecret = datas[2].substring(datas[2].indexOf('=') + 1);
                tokenAccess.nsid = datas[3].substring(datas[3].indexOf('=') + 1);
                tokenAccess.username = URLDecoder.decode(datas[4].substring(datas[4].indexOf('=') + 1), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private GetRequestTokenRequest getRequestTokenRequest;
    private GetAccessTokenRequest getAccessTokenRequest;

    @Override
    public void authentification(Context context, AuthentificationInterface listener) {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<APIAccount> getAccounts() {
        return Collections.emptyList();
    }

    @Override
    public void addAccount(APIAccount apiAccount) {

    }

    private boolean isGetAccessTokenRequestRunning() {
        return (getAccessTokenRequest != null && !getAccessTokenRequest.isRunning());
    }

    private boolean isGetRequestTokenRequestRunning() {
        return (getRequestTokenRequest != null && !getRequestTokenRequest.isRunning());
    }

    private void requestTokenRequest(Context context) {
        if (!isGetRequestTokenRequestRunning()) {
            getRequestTokenRequest = new GetRequestTokenRequest(context, new GetRequestTokenInterface() {
                @Override
                public void onFinish(TokenRequest tokenRequest) {
                    Flikr.this.tokenRequest = tokenRequest;
                    requestUserPermission(tokenRequest);
                }

                @Override
                public void onError(int code) {
                }
            });
            getRequestTokenRequest.execute();
        }
    }

    /*private void retrieveToken(String response) {
        TokenRequest tokenRequest = new TokenRequest();
        String[] datas = response.split("&");

        if (datas.length == 3) {
            tokenRequest.callbackConfirmed = datas[0].substring(datas[0].indexOf('=') + 1).equals("true");
            tokenRequest.token = datas[1].substring(datas[1].indexOf('=') + 1);
            tokenRequest.tokenSecret = datas[2].substring(datas[2].indexOf('=') + 1);
        }

        Log.i("token request", tokenRequest.callbackConfirmed + " " + tokenRequest.token + " " + tokenRequest.tokenSecret);
        this.tokenRequest = tokenRequest;
    }*/

}
