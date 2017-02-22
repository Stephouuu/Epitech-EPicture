package fr.epicture.epicture.api.flickr.requests;

import android.content.Context;
import android.support.annotation.NonNull;

import fr.epicture.epicture.api.flickr.FlickrClient;
import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.requests.TextRequest;

/**
 * Created by Stephane on 21/02/2017.
 */

public class GetCommentsRequest extends TextRequest {

    private static final String URL = "https://www.flickr.com/services/rest";
    private static final String METHOD = "flickr.photos.comments.getList";

    public GetCommentsRequest(@NonNull Context context, String photoid, LoadTextInterface listener) {
        super(context, URL + "?nojsoncallback=1"
                + "&format=json"
                + "&api_key=" + FlickrClient.CONSUMER_KEY
                + "&photo_id=" + photoid
                + "&method=" + METHOD,
                listener);
        execute();
    }
}
