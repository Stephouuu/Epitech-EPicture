package fr.epicture.epicture.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.epicture.epicture.R;
import fr.epicture.epicture.api.API;
import fr.epicture.epicture.api.APIAccount;
import fr.epicture.epicture.api.APIImageElement;
import fr.epicture.epicture.api.APIManager;
import fr.epicture.epicture.fragments.ImageListFragment;
import fr.epicture.epicture.interfaces.ImageListInterface;
import fr.epicture.epicture.interfaces.LoadBitmapInterface;
import fr.epicture.epicture.interfaces.LoadImageElementInterface;
import jp.wasabeef.blurry.Blurry;


public class MainActivity extends AppCompatActivity implements ImageListInterface {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private CircleImageView profilePic;
    private ImageView profilePicBlurred;
    private TextView myPictures;

    private ImageListFragment imageListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_flickr_activity);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout)findViewById(R.id.main_drawer);
        profilePic = (CircleImageView)findViewById(R.id.main_profilepic);
        profilePicBlurred = (ImageView)findViewById(R.id.main_profilepic_blurred);
        myPictures = (TextView)findViewById(R.id.drawer_mypictures);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.mipmap.hamburger);
        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout)findViewById(R.id.main_drawer);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }

                refreshProfilePicBlurred();
            }
        });

        myPictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyPicturesActivity.class);
                startActivity(intent);
            }
        });

        refreshFragment();
    }

    @Override
    public void onBackPressed() {
    }

    private void refreshProfilePicBlurred() {
        API api = APIManager.getSelectedAPI();
        APIAccount user = api.getCurrentAccount();

        api.loadUserAvatar(this, user.username, new LoadBitmapInterface() {
            @Override
            public void onFinish(Bitmap bitmap) {
                profilePic.setImageBitmap(bitmap);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Blurry.with(MainActivity.this).radius(25).sampling(2).capture(profilePic).into(profilePicBlurred);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }, 10);
            }
        });
    }

    private void refreshFragment() {
        imageListFragment = new ImageListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, imageListFragment).commit();
    }

    @Override
    public void onRequestImageList(int page) {
        API api = APIManager.getSelectedAPI();
        api.getInterestingnessList(this, page, new LoadImageElementInterface() {
            @Override
            public void onFinish(List<APIImageElement> result, boolean error) {
                if (!error) {
                    imageListFragment.refreshList(result);
                }
            }
        });
        imageListFragment.refreshList(null);
    }

}
