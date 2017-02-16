package fr.epicture.epicture.api.imgur;

import android.util.SparseArray;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.epicture.epicture.api.API;
import fr.epicture.epicture.api.APIAccount;
import fr.epicture.epicture.api.APIType;
import fr.epicture.epicture.api.flikr.FlikrAccount;

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

    private final Map<Integer, ImgurAccount> accountByID = new HashMap();

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================

    public Imgur() {
    }

    // ========================================================================
    // METHODS
    // ========================================================================


    @Override
    public Collection<? extends APIAccount> getAccounts() {
        return accountByID.values();
    }

    public void addAccount(ImgurAccount user) {
        accountByID.put(user.getAccountId(), user);
    }

    public void removeAccount(ImgurAccount user) {
        accountByID.remove(user.getAccountId());
    }

    @Override
    public String getAuthorizeLink() {
        return authorizeLink;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public APIType getType() {
        return APIType.IMGUR;
    }

    @Override
    public void addAccount(APIAccount apiAccount) throws ClassCastException {
        accountByID.put(apiAccount.getAccountId(), ((ImgurAccount) apiAccount));
    }
}
