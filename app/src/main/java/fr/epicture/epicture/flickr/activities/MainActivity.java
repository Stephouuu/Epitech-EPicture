package fr.epicture.epicture.flickr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.epicture.epicture.R;
import fr.epicture.epicture.flickr.interfaces.AuthentificationManagerInterface;
import fr.epicture.epicture.flickr.model.TokenAccess;
import fr.epicture.epicture.flickr.model.TokenRequest;
import fr.epicture.epicture.flickr.utils.AuthentificationManager;


public class MainActivity extends AppCompatActivity implements AuthentificationManagerInterface {

    private static final int REQUEST_USER_PERMISSION = 1;

    private AuthentificationManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        am = new AuthentificationManager(this, this);
        am.authentify();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_USER_PERMISSION) {
            if (resultCode == RESULT_OK) {
                TokenRequest tokenRequest = data.getParcelableExtra(FlickrUserPermissionActivity.EXTRA_TOKENREQUEST);
                am.getAccessToken(tokenRequest);
            }
        }
    }

    @Override
    public void onRequestUserPermission(final TokenRequest tokenRequest) {
        Intent intent = new Intent(this, FlickrUserPermissionActivity.class);
        FlickrUserPermissionActivity.setExtraTokenrequest(intent, tokenRequest);
        startActivityForResult(intent, REQUEST_USER_PERMISSION);
    }

    @Override
    public void onFinish(TokenAccess tokenAccess) {

    }
}
