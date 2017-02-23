package fr.epicture.epicture.api.imgur.requests;

import android.content.Context;
import android.support.annotation.NonNull;

import fr.epicture.epicture.interfaces.LoadImageElementInterface;
import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.requests.TextRequest;

public class AlbumRequest extends TextRequest {

    private static final String URL = "https://api.imgur.com/3/account/%s/albums/";

    public AlbumRequest(@NonNull Context context, String username, String accessToken, LoadTextInterface listener) {
        super(context, String.format(URL, username), listener);
        addHeader("Authorization", "Bearer " + accessToken);
    }
}
