package fr.epicture.epicture.api.flikr;


import java.util.Map;

import fr.epicture.epicture.api.APIAccount;

public class FlikrAccount extends APIAccount {

    public FlikrAccount(Map<String, String> params) throws InstantiationException {
        super(params.get("account_id"), params.get("account_username"));
    }
}
