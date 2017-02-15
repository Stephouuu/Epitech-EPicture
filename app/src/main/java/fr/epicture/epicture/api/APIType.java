package fr.epicture.epicture.api;

import java.util.Map;
import java.util.function.Consumer;

import fr.epicture.epicture.api.flikr.FlikrAccount;
import fr.epicture.epicture.api.imgur.ImgurAccount;

public enum APIType {
    IMGUR(ImgurAccount::new),
    FLIKR(FlikrAccount::new);

    // ========================================================================
    // FIELDS
    // ========================================================================

    private final AccountInstantiate accountInstantiate;

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================

    APIType(AccountInstantiate accountInstantiate) {
        this.accountInstantiate = accountInstantiate;
    }

    // ========================================================================
    // METHODS
    // ========================================================================

    public APIAccount newAccount(Map<String, String> params) throws InstantiationException
    {
        return accountInstantiate.instantiate(params);
    }
}
