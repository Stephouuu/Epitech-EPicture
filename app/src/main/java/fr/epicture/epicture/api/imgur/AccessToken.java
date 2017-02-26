package fr.epicture.epicture.api.imgur;

public class AccessToken {

    // ========================================================================
    // FIELDS
    // ========================================================================

    private long date;
    private long expiration; // in second
    private String accessToken;

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================

    public AccessToken(String accessToken, long date, long expiration) {
        this.accessToken = "";
        update(accessToken, date, expiration);
    }

    // ========================================================================
    // METHODS
    // ========================================================================

    public void update(String accessToken, long date, long expiration)
    {
        this.date = date;
        this.expiration = expiration;
        this.accessToken = accessToken;
    }

    // ------------------------------------------------------------------------
    // Getter
    // ------------------------------------------------------------------------

    public long getDate() {
        return date;
    }

    public long getExpiration() {
        return expiration;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
