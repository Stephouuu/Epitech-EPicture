package fr.epicture.epicture.api.flickr.requests;

import android.content.Context;
import android.support.annotation.NonNull;

import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.requests.TextRequest;

/**
 * Created by Stephane on 21/02/2017.
 */

public class DeletePhotoRequest extends TextRequest {

    public DeletePhotoRequest(@NonNull Context context, String url, LoadTextInterface listener) {
        super(context, url, listener);
    }

}
