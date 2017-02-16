package fr.epicture.epicture.api.flikr;

import java.util.Collection;
import java.util.Collections;

import fr.epicture.epicture.api.API;
import fr.epicture.epicture.api.APIAccount;
import fr.epicture.epicture.api.APIType;

public class Flikr implements API {
    private static final String authorizeLink = "TODO";
    private static final String name = "Flikr";

    @Override
    public String getAuthorizeLink() {
        return authorizeLink;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<APIAccount> getAccounts() {
        return Collections.emptyList();
    }

    @Override
    public APIType getType() {
        return APIType.FLIKR;
    }

    @Override
    public void addAccount(APIAccount apiAccount) {

    }
}
