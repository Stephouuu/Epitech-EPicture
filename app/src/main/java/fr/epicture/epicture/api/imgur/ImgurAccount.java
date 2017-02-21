package fr.epicture.epicture.api.imgur;

import java.util.Map;

import fr.epicture.epicture.api.APIAccount;

public class ImgurAccount extends APIAccount {

    // ========================================================================
    // FIELDS
    // ========================================================================

    private int date;
    private int expiration; // in second
    private String refreshToken;
    private String accessToken;

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================

    public ImgurAccount(Map<String, String> params) throws InstantiationException {
        super(params.get("account_id"), params.get("account_username"));
    }

    // ========================================================================
    // METHODS
    // ========================================================================

    // ------------------------------------------------------------------------
    // Getter / Setter
    // ------------------------------------------------------------------------

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getID() {
        return id;
    }
}
