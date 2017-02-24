package fr.epicture.epicture.api.imgur.requests;

import android.content.Context;
import android.support.annotation.NonNull;

import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.requests.TextRequest;

public class UserInformationRequest extends TextRequest {

    // ========================================================================
    // STATIC FIELDS
    // ========================================================================

    private static final String BASE_URL = "https://api.imgur.com/3/account/";

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================

    public UserInformationRequest(@NonNull Context context, String username, String accessToken, LoadTextInterface listener) {
        super(context, BASE_URL + username, listener);
        addHeader("Authorization", "Bearer " + accessToken);
    }
}
