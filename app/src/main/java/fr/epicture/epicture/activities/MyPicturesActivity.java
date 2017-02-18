package fr.epicture.epicture.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.List;

import fr.epicture.epicture.R;
import fr.epicture.epicture.api.API;
import fr.epicture.epicture.api.APIImageElement;
import fr.epicture.epicture.api.APIManager;
import fr.epicture.epicture.fragments.ImageListFragment;
import fr.epicture.epicture.interfaces.ImageListInterface;
import fr.epicture.epicture.interfaces.LoadImageElementInterface;

public class MyPicturesActivity extends AppCompatActivity implements ImageListInterface {

    private ImageListFragment imageListFragment;

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
        API api = APIManager.getSelectedAPI();
        api.getMyPictures(this, page, new LoadImageElementInterface() {
            @Override
            public void onFinish(List<APIImageElement> result, boolean error) {
                if (!error) {
                    imageListFragment.refreshList(result);
                }
            }
        });
    }


}
