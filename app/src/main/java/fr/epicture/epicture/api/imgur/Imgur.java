package fr.epicture.epicture.api.imgur;

import android.content.Context;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.epicture.epicture.R;
import fr.epicture.epicture.api.API;
import fr.epicture.epicture.api.APIAccount;
import fr.epicture.epicture.api.APIImageElement;
import fr.epicture.epicture.api.imgur.utils.ImgurUtils;
import fr.epicture.epicture.interfaces.AuthentificationInterface;
import fr.epicture.epicture.interfaces.LoadBitmapInterface;
import fr.epicture.epicture.interfaces.LoadImageElementInterface;
import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.interfaces.LoadUserInfoInterface;

public class Imgur implements API {

    // ========================================================================
    // STATIC FIELDS
    // ========================================================================

    private static final String CLIENT_ID = "a754d8ce4c94e1c";
    private static final String CLIENT_SECRET = "e6020ba810e1e7d85a756a26a666d8ea3b60a6f1";
    private static final String authorizeLink = "https://api.imgur.com/oauth2/authorize?client_id=" + CLIENT_ID + "&response_type=token";
    private static final String name = "Imgur";

    // ========================================================================
    // FIELDS
    // ========================================================================

    private final Map<String, APIAccount> accountByID = new HashMap();

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================

    public Imgur() {
    }

    // ========================================================================
    // METHODS
    // ========================================================================


    @Override
    public Collection<APIAccount> getAccounts() {
        return accountByID.values();
    }

    public void addAccount(ImgurAccount user) {
        accountByID.put(user.id, user);
    }

    public void removeAccount(ImgurAccount user) {
        accountByID.remove(user.id);
    }

    private AuthentificationInterface listener;

    @Override
    public void setAuthentificationListener(AuthentificationInterface listener) {
        this.listener = listener;
    }

    @Override
    public void authentification(Context context) {
        listener.onUserPermissionRequest(authorizeLink, "account_username");
    }

    @Override
    public void afterUserPermissionRequest(Context context, String urlResponse) {
        final Map<String, String> params = ImgurUtils.getQueryMap(urlResponse);
        listener.onUserPermissionGranted();
    }

    //@Override
    public void loadAccounts() {
    }

    @Override
    public void loadUserInformation(Context context, LoadUserInfoInterface callback) {
        // get user information from the imgur server (if possible)
    }

    @Override
    public void loadUserAvatar(Context context, String id, LoadBitmapInterface callback) {

    }

    @Override
    public void loadImage(Context context, APIImageElement element, LoadBitmapInterface callback) {

    }

    @Override
    public void uploadImage(Context context, APIAccount user, String path, String title, String description, LoadTextInterface callback) {

    }

    @Override
    public void getInterestingnessList(Context context, int page, LoadImageElementInterface callback) {

    }

    @Override
    public void getMyPictures(Context context, int page, LoadImageElementInterface callback) {

    }

    @Override
    public void setCurrentAccount(APIAccount account) {

    }

    @Override
    public APIAccount getCurrentAccount() {
        return null;
    }

    @Override
    public int getLogo() {
        return R.drawable.imgur_logo;
    }

    @Override
    public String getName() {
        return name;
    }
}
