package fr.epicture.epicture.api.imgur.requests;

import android.content.Context;
import android.support.annotation.NonNull;

import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.requests.TextPostRequest;


public class AddFavoriteRequest extends TextPostRequest {

    private static final String URL = "https://api.imgur.com/3/image/%s/favorite";

    private String photoid;
    private String token;

    public AddFavoriteRequest(@NonNull Context context, String photoid, String token, LoadTextInterface listener) {
        super(context, String.format(URL, photoid), listener);

        this.token = token;

        addHeader();

        execute();
    }

    private void addHeader() {
        try {
            addHeader("Authorization", "Bearer " + token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
