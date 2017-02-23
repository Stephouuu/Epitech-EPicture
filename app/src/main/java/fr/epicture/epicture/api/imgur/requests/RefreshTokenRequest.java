package fr.epicture.epicture.api.imgur.requests;

import android.content.Context;
import android.support.annotation.NonNull;

import fr.epicture.epicture.api.imgur.Imgur;
import fr.epicture.epicture.api.imgur.ImgurAccount;
import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.requests.TextRequest;

public class RefreshTokenRequest extends TextRequest {

    private static final String REQUEST = "https://api.imgur.com/oauth2/token?";

    public RefreshTokenRequest(@NonNull Context context, String refreshToken, LoadTextInterface listener) {
        super(context, REQUEST
                + "refresh_token=" + refreshToken
                + "&client_id=" + Imgur.CLIENT_ID
                + "&client_secret=" + Imgur.CLIENT_SECRET
                + "&grant_type=" + refreshToken
                , listener);
    }
}
