package fr.epicture.epicture.api.imgur.requests;

import android.content.Context;
import android.support.annotation.NonNull;

import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.requests.TextRequest;

public class GalleryRequest extends TextRequest {

    private static final String URL = "https://api.imgur.com/3/gallery/%s/%s/%d";

    public GalleryRequest(@NonNull Context context, String section, String sort, int page, LoadTextInterface listener) {
        super(context, String.format(URL, section, sort, page), listener);
    }
}
