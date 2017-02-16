package fr.epicture.epicture.flickr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONObject;

import fr.epicture.epicture.R;
import fr.epicture.epicture.flickr.interfaces.AuthentificationManagerInterface;
import fr.epicture.epicture.flickr.interfaces.UserInformationInterface;
import fr.epicture.epicture.flickr.model.TokenAccess;
import fr.epicture.epicture.flickr.model.TokenRequest;
import fr.epicture.epicture.flickr.requests.UserInformationRequest;
import fr.epicture.epicture.flickr.utils.AuthentificationManager;
import fr.epicture.epicture.flickr.utils.FlickrClient;
import fr.epicture.epicture.flickr.utils.Preferences;
import fr.epicture.epicture.flickr.utils.UserElement;

public class StartUpActivity extends AppCompatActivity implements AuthentificationManagerInterface {

    private static final int REQUEST_USER_PERMISSION = 1;

    private AuthentificationManager am;
    private UserInformationRequest userInformationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup_activity);


        FlickrClient.tokenAccess = Preferences.getAccessToken(getApplicationContext());
        if (FlickrClient.tokenAccess == null) {
            am = new AuthentificationManager(this, this);
            am.authentify();
        } else {
            requestUserInformation();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_USER_PERMISSION) {
            if (resultCode == RESULT_OK) {
                TokenRequest tokenRequest = data.getParcelableExtra(UserPermissionActivity.EXTRA_TOKENREQUEST);
                am.getAccessToken(tokenRequest);
            }
        }
    }

    private void requestUserInformation() {
        if (!isRequestingUserInfo()) {
            userInformationRequest = new UserInformationRequest(this, FlickrClient.tokenAccess.nsid, new UserInformationInterface() {
                @Override
                public void onFinish(JSONObject jsonObject) {
                    userInformationRequest = null;
                    FlickrClient.user = new UserElement(jsonObject);
                    Intent intent = new Intent(StartUpActivity.this, FlickrMainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                @Override
                public void onError(int code) {
                    // can not log in
                }
            });
            userInformationRequest.execute();
        }
    }

    public boolean isRequestingUserInfo() {
        return (userInformationRequest != null && !userInformationRequest.isRunning());
    }

    @Override
    public void onRequestUserPermission(final TokenRequest tokenRequest) {
        Intent intent = new Intent(this, UserPermissionActivity.class);
        UserPermissionActivity.setExtraTokenrequest(intent, tokenRequest);
        startActivityForResult(intent, REQUEST_USER_PERMISSION);
    }

    @Override
    public void onAuthentificationFinished(TokenAccess tokenAccess) {
        Preferences.setAccessToken(getApplicationContext(), tokenAccess);
        FlickrClient.tokenAccess = tokenAccess;
        requestUserInformation();
    }

}
