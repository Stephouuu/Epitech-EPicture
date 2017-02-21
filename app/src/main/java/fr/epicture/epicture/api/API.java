package fr.epicture.epicture.api;

import android.content.Context;

import java.util.Collection;

import fr.epicture.epicture.interfaces.AuthentificationInterface;
import fr.epicture.epicture.interfaces.LoadBitmapInterface;
import fr.epicture.epicture.interfaces.LoadImageElementInterface;
import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.interfaces.LoadUserInfoInterface;

public interface API {

    int getLogo();
    String getName();

    void setAuthentificationListener(AuthentificationInterface listener);
    void authentification(Context context);
    void afterUserPermissionRequest(Context context, String urlResponse);

    void loadUserInformation(Context context, LoadUserInfoInterface callback);
    void loadUserInformation(Context context, String id, LoadUserInfoInterface callback);
    //void loadUserAvatar(Context context, String id, LoadBitmapInterface callback);
    void loadUserAvatar(Context context, APIAccount account, LoadBitmapInterface callback);

    void loadImage(Context context, APIImageElement element, LoadBitmapInterface callback);
    void uploadImage(Context context, APIAccount user, String path, String title, String description, LoadTextInterface callback);

    void getInterestingnessList(Context context, int page, LoadImageElementInterface callback);
    void getMyPictures(Context context, int page, LoadImageElementInterface callback);

    void search(Context context, String search, String userid, int page, LoadImageElementInterface callback);

    void setCurrentAccount(APIAccount account);
    APIAccount getCurrentAccount();
    Collection<APIAccount> getAccounts();
}
