package fr.epicture.epicture.api.flickr;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.epicture.epicture.R;
import fr.epicture.epicture.api.API;
import fr.epicture.epicture.api.APIAccount;
import fr.epicture.epicture.api.flickr.database.FlickrDatabase;
import fr.epicture.epicture.api.flickr.interfaces.GetAccessTokenInterface;
import fr.epicture.epicture.api.flickr.interfaces.GetRequestTokenInterface;
import fr.epicture.epicture.api.flickr.interfaces.ImageDiskCacheInterface;
import fr.epicture.epicture.api.flickr.interfaces.UserInformationInterface;
import fr.epicture.epicture.api.flickr.modele.TokenAccess;
import fr.epicture.epicture.api.flickr.modele.TokenRequest;
import fr.epicture.epicture.api.flickr.requests.GetAccessTokenRequest;
import fr.epicture.epicture.api.flickr.requests.GetRequestTokenRequest;
import fr.epicture.epicture.api.flickr.requests.UserInformationRequest;
import fr.epicture.epicture.api.flickr.utils.ImageDiskCache;
import fr.epicture.epicture.api.flickr.utils.ImageElement;
import fr.epicture.epicture.interfaces.AuthentificationInterface;
import fr.epicture.epicture.interfaces.LoadBitmapInterface;
import fr.epicture.epicture.interfaces.LoadUserInfoInterface;

public class Flickr implements API {

    //private TokenRequest tokenRequest;
    //private TokenAccess tokenAccess;

    private static final String authorizeLink = "https://www.flickr.com/services/oauth/authorize?oauth_token=%s&perms=delete";
    private static final String name = "Flickr";

    private static final Map<String, FlickrAccount> Accounts = new HashMap<>();

    private FlickrDatabase database;

    private GetRequestTokenRequest getRequestTokenRequest;
    private GetAccessTokenRequest getAccessTokenRequest;
    private UserInformationRequest userInformationRequest;

    private AuthentificationInterface authListener;
    private TokenRequest tokenRequest;

    private FlickrAccount currentAccount;

    public Flickr(Context context) {
        database = new FlickrDatabase(context);
        database.open();
        loadAccounts();
    }

    @Override
    public void setAuthentificationListener(AuthentificationInterface listener) {
        this.authListener = listener;
    }

    @Override
    public void authentification(Context context) {
        if (!isGetRequestTokenRequestRunning()) {
            getRequestTokenRequest = new GetRequestTokenRequest(context, new GetRequestTokenInterface() {
                @Override
                public void onFinish(TokenRequest tokenRequest) {
                    getRequestTokenRequest = null;
                    Flickr.this.tokenRequest = tokenRequest;
                    authListener.onUserPermissionRequest(String.format(authorizeLink, tokenRequest.token), "oauth_verifier");
                }

                @Override
                public void onError(int code) {
                }
            });
            getRequestTokenRequest.execute();
        }
    }

    @Override
    public void afterUserPermissionRequest(Context context, String urlResponse) {
        if (!isGetAccessTokenRequestRunning()) {
            tokenRequest.verifier = Uri.parse(urlResponse).getQueryParameter("oauth_verifier");
            getAccessTokenRequest = new GetAccessTokenRequest(context, tokenRequest, new GetAccessTokenInterface() {
                @Override
                public void onFinish(TokenAccess tokenAccess) {
                    getAccessTokenRequest = null;
                    FlickrAccount user = new FlickrAccount(tokenAccess);
                    database.insertAccount(user);
                    Accounts.put(user.username, user);
                    authListener.onUserPermissionGranted();
                }

                @Override
                public void onError(int code) {
                }
            });
            getAccessTokenRequest.execute();
        }
    }

    @Override
    public void loadUserInformation(Context context, LoadUserInfoInterface callback) {
        for (Map.Entry<String, FlickrAccount> entry : Accounts.entrySet()) {
            FlickrAccount user = entry.getValue();
            userInformationRequest = new UserInformationRequest(context, user.nsid, new UserInformationInterface() {
                @Override
                public void onFinish(JSONObject jsonObject) {
                    userInformationRequest = null;
                    user.setData(jsonObject);
                    callback.onFinish(user.username, user);
                }

                @Override
                public void onError(int code) {

                }
            });
            userInformationRequest.execute();
        }
    }

    @Override
    public void loadUserAvatar(Context context, String id, LoadBitmapInterface callback) {
        FlickrAccount user = Accounts.get(id);
        ImageElement imageElement = new ImageElement(user.iconfarm, user.iconserver, user.nsid);
        new ImageDiskCache().load(context, imageElement, new ImageDiskCacheInterface() {
            @Override
            public void onFinish(ImageElement imageElement, Bitmap bitmap) {
                user.profilePic = imageElement.getFilePath(context);
                Accounts.put(id, user);
                callback.onFinish(bitmap);
            }
        });
    }

    @Override
    public void setCurrentAccount(APIAccount account) {
        currentAccount = Accounts.get(account.username);
    }

    @Override
    public APIAccount getCurrentAccount() {
        return currentAccount;
    }

    @Override
    public int getLogo() {
        return R.drawable.flickr_logo;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<APIAccount> getAccounts() {
        Collection<APIAccount> ret = new ArrayList<>();
        List<FlickrAccount> accounts = database.getAccounts();
        for (FlickrAccount account : accounts) {
            ret.add(account);
        }
        return (ret);
    }

    private void loadAccounts() {
        List<FlickrAccount> accounts = database.getAccounts();
        for (FlickrAccount account : accounts) {
            Accounts.put(account.username, account);
        }
    }

    private boolean isGetAccessTokenRequestRunning() {
        return (getAccessTokenRequest != null && !getAccessTokenRequest.isRunning());
    }

    private boolean isGetRequestTokenRequestRunning() {
        return (getRequestTokenRequest != null && !getRequestTokenRequest.isRunning());
    }

}
