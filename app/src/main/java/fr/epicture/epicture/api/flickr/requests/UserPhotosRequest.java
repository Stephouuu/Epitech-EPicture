package fr.epicture.epicture.api.flickr.requests;

import android.content.Context;
import android.support.annotation.NonNull;

import fr.epicture.epicture.api.flickr.FlickrAccount;
import fr.epicture.epicture.api.flickr.FlickrClient;
import fr.epicture.epicture.api.flickr.utils.FlickrUtils;
import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.requests.TextRequest;
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

public class UserPhotosRequest extends TextRequest {

    private static final String URL = "https://www.flickr.com/services/rest";
    private static final String METHOD = "flickr.people.getPhotos";

    private int page;
    private FlickrAccount user;

    public UserPhotosRequest(@NonNull Context context, int page, FlickrAccount user, LoadTextInterface listener) {
        super(context, listener);
        this.page = page;
        this.user = user;

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

        String[] params = new String[] {
                "nojsoncallback=1",
                "oauth_nonce=" + random,
                "format=json",
                "oauth_consumer_key=" + FlickrClient.CONSUMER_KEY,
                "oauth_timestamp=" + unixTime,
                "oauth_signature_method=HMAC-SHA1",
                "oauth_token=" + user.token,
                "method=" + METHOD,
                "page=" + page,
                "per_page=20",
                "user_id=" + user.id
        };

        return URL + "?nojsoncallback=1"
                + "&oauth_nonce=" + random
                + "&format=json"
                + "&oauth_consumer_key=" + FlickrClient.CONSUMER_KEY
                + "&oauth_timestamp=" + unixTime
                + "&oauth_signature_method=HMAC-SHA1"
                + "&oauth_token=" + user.token
                + "&oauth_signature=" + FlickrUtils.getURLSignature(part1, part2, params, user.tokenSecret)
                + "&method=" + METHOD
                + "&page=" + page
                + "&per_page=20"
                + "&user_id=" + user.id;
    }
}
