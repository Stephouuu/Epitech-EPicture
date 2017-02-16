package fr.epicture.epicture.api;

import java.util.Map;

import fr.epicture.epicture.api.imgur.ImgurAccount;

public abstract class APIAccount {

    // ========================================================================
    // FIELDS
    // ========================================================================

    private final int accountId;
    private final String username;

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================

    public APIAccount(String accountId, String username) throws InstantiationException {
        if (accountId == null || username == null)
            throw new InstantiationException();
        this.accountId = Integer.parseInt(accountId);
        this.username = username;
    }

    // ========================================================================
    // METHODS
    // ========================================================================

    public int getAccountId() {
        return accountId;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        APIAccount that = (APIAccount) o;
        return accountId == that.accountId;
    }

    @Override
    public int hashCode() {
        return accountId;
    }
}
