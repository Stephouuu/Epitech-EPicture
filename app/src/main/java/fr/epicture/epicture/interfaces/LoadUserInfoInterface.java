package fr.epicture.epicture.interfaces;

import fr.epicture.epicture.api.APIAccount;

/**
 * Created by Stephane on 17/02/2017.
 */

public interface LoadUserInfoInterface {

    void onFinish(String id, APIAccount result);
}
