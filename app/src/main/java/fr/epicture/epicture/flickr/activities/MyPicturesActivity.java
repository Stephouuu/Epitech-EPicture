package fr.epicture.epicture.flickr.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.epicture.epicture.R;
import fr.epicture.epicture.flickr.fragments.ImageListFragment;
import fr.epicture.epicture.flickr.interfaces.ImageListInterface;
import fr.epicture.epicture.flickr.interfaces.UserPhotosRequestInterface;
import fr.epicture.epicture.flickr.requests.UserPhotosRequest;
import fr.epicture.epicture.flickr.utils.FlickrClient;
import fr.epicture.epicture.flickr.utils.ImageElement;

public class MyPicturesActivity extends AppCompatActivity implements ImageListInterface {

    private ImageListFragment imageListFragment;

    private UserPhotosRequest userPhotosRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_pictures_activity);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        refreshFragment();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void refreshFragment() {
        imageListFragment = new ImageListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, imageListFragment).commit();
    }

    @Override
    public void onRequestImageList(int page) {
        if (!isRequestingImageList()) {
            userPhotosRequest = new UserPhotosRequest(this, 1, FlickrClient.user.id, new UserPhotosRequestInterface() {
                @Override
                public void onFinish(JSONObject jsonObject) {
                    userPhotosRequest = null;
                    if (jsonObject != null) {
                        try {
                            int maxPage = jsonObject.getJSONObject("photos").getInt("pages");
                            imageListFragment.setMaxPage(maxPage);
                            List<ImageElement> imageElementList = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONObject("photos").getJSONArray("photo");
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    ImageElement imageElement = new ImageElement(jsonArray.getJSONObject(i),
                                            ImageElement.TYPE_IMAGE, ImageElement.SIZE_PREVIEW);
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
                public void onError(int code) {

                }
            });
            userPhotosRequest.execute();
        }
    }

    public boolean isRequestingImageList() {
        return (userPhotosRequest != null && !userPhotosRequest.isRunning());
    }
}
