package fr.epicture.epicture.api.imgur.requests;

import android.content.Context;
import android.support.annotation.NonNull;

import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.requests.TextPostRequest;

public class AlbumRequest extends TextPostRequest {

    private static final String URL = "https://api.imgur.com/3/account/%s/albums/%d";

    public AlbumRequest(@NonNull Context context, String username, String accessToken, LoadTextInterface listener) {
        super(context, String.format(URL, username, 1), listener);
        addHeader("Authorization", "Bearer " + accessToken);
    }
}
