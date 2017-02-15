package fr.epicture.epicture.api;

import java.util.Collection;

public interface API {

    APIType getType();

    String getAuthorizeLink();

    String getName();

    Collection<? extends APIAccount> getAccounts();

    void addAccount(APIAccount apiAccount);
}
