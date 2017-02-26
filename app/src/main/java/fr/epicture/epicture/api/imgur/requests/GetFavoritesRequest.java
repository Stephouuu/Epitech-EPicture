package fr.epicture.epicture.api.imgur.requests;

import android.content.Context;
import android.support.annotation.NonNull;

import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.requests.TextRequest;

/**
 * Created by Stephane on 26/02/2017.
 */

public class GetFavoritesRequest extends TextRequest {

    //private static final String URL = "https://api.imgur.com/3/account/{username}/favorites/{page}/{sort}";
    private static final String URL = "https://api.imgur.com/3/account/%1$s/favorites/%2$s";

    public GetFavoritesRequest(@NonNull Context context, String username, String token, int page, LoadTextInterface listener) {
        super(context, listener);

        setUrl(String.format(URL, username, page));
        addHeader("Authorization", "Bearer " + token);

        execute();
    }
}
