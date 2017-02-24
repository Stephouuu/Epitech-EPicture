package fr.epicture.epicture.api.flickr.requests;

import android.content.Context;
import android.support.annotation.NonNull;

import fr.epicture.epicture.api.flickr.FlickrClient;
import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.requests.TextRequest;

/**
 * Created by Stephane on 24/02/2017.
 */

public class GetUserFavoritesRequest extends TextRequest {

    private static final String URL = "https://www.flickr.com/services/rest";
    private static final String METHOD = "flickr.favorites.getList";

    public GetUserFavoritesRequest(@NonNull Context context, String userid, LoadTextInterface listener) {
        super(context,
                URL + "?nojsoncallback=1"
                + "&format=json"
                + "&api_key=" + FlickrClient.CONSUMER_KEY
                + "&user_id=" + userid
                + "&method=" + METHOD
                , listener);
        execute();
    }
}
