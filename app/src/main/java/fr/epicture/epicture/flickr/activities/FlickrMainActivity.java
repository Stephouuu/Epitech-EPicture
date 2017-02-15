package fr.epicture.epicture.flickr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.epicture.epicture.R;
import fr.epicture.epicture.flickr.fragments.ImageListFragment;
import fr.epicture.epicture.flickr.interfaces.AuthentificationManagerInterface;
import fr.epicture.epicture.flickr.interfaces.ImageListInterface;
import fr.epicture.epicture.flickr.interfaces.InterestingnessRequestInterface;
import fr.epicture.epicture.flickr.model.TokenAccess;
import fr.epicture.epicture.flickr.model.TokenRequest;
import fr.epicture.epicture.flickr.requests.InterestingnessRequest;
import fr.epicture.epicture.flickr.utils.AuthentificationManager;
import fr.epicture.epicture.flickr.utils.FlickrClient;
import fr.epicture.epicture.flickr.utils.ImageElement;
import fr.epicture.epicture.flickr.utils.Preferences;


public class FlickrMainActivity extends AppCompatActivity implements AuthentificationManagerInterface, ImageListInterface {

    private static final int REQUEST_USER_PERMISSION = 1;

    private AuthentificationManager am;
    private InterestingnessRequest interestingnessRequest;

    private ImageListFragment imageListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Toolbar toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        FlickrClient.tokenAccess = Preferences.getAccessToken(getApplicationContext());
        if (FlickrClient.tokenAccess == null) {
            am = new AuthentificationManager(this, this);
            am.authentify();
        } else {
            startInterestingnessList();
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

    private void startInterestingnessList() {
        imageListFragment = new ImageListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, imageListFragment).commit();
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
        startInterestingnessList();
    }

    @Override
    public void onRequestImageList(int page) {
        if (!isRequestingImageList()) {
            interestingnessRequest = new InterestingnessRequest(this, FlickrClient.tokenAccess, page, new InterestingnessRequestInterface() {
                @Override
                public void onFinish(JSONObject jsonObject) {
                    interestingnessRequest = null;
                    if (jsonObject != null) {
                        try {
                            List<ImageElement> imageElementList = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONObject("photos").getJSONArray("photo");
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    ImageElement imageElement = new ImageElement(jsonArray.getJSONObject(i), ImageElement.SIZE_THUMBNAIL);
                                    imageElementList.add(imageElement);
                                }
                                imageListFragment.refreshList(imageElementList);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onError(int responseCode) {
                }
            });
            interestingnessRequest.execute();
            imageListFragment.refreshList(null);
        }
    }

    public boolean isRequestingImageList() {
        return (interestingnessRequest != null && !interestingnessRequest.isRunning());
    }
}
