package fr.epicture.epicture.api.imgur.requests;

import android.content.Context;
import android.support.annotation.NonNull;

import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.requests.TextRequest;

public class UserInformationRequest extends TextRequest {

    // ========================================================================
    // STATIC FIELDS
    // ========================================================================

    private static final String URL = "https://api.imgur.com/3/account/me";

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================

    public UserInformationRequest(@NonNull Context context, String username, String accessToken, LoadTextInterface listener) {
        super(context, URL, listener);
        addHeader("Authorization", "Bearer " + accessToken);
    }
}
