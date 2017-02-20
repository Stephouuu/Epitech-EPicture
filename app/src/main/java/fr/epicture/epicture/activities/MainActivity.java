package fr.epicture.epicture.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.epicture.epicture.R;
import fr.epicture.epicture.adapters.MainDrawerListAdapter;
import fr.epicture.epicture.api.API;
import fr.epicture.epicture.api.APIAccount;
import fr.epicture.epicture.api.APIImageElement;
import fr.epicture.epicture.api.APIManager;
import fr.epicture.epicture.fragments.ImageListFragment;
import fr.epicture.epicture.interfaces.ImageListInterface;
import fr.epicture.epicture.interfaces.LoadBitmapInterface;
import fr.epicture.epicture.interfaces.LoadImageElementInterface;
import fr.epicture.epicture.interfaces.MainDrawerInterface;
import fr.epicture.epicture.interfaces.RetractableToolbarInterface;
import jp.wasabeef.blurry.Blurry;


public class MainActivity extends AppCompatActivity implements ImageListInterface, RetractableToolbarInterface {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private CircleImageView profilePic;
    private ImageView profilePicBlurred;

    private ListView drawerList;
    private MainDrawerListAdapter drawerListAdapter;

    private ImageListFragment imageListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout)findViewById(R.id.main_drawer);
        profilePic = (CircleImageView)findViewById(R.id.main_profilepic);
        profilePicBlurred = (ImageView)findViewById(R.id.main_profilepic_blurred);
        drawerList = (ListView) findViewById(R.id.drawer_list);

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

            }
        });

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerList.setSelection(position);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        drawerListAdapter = new MainDrawerListAdapter(this, new MainDrawerInterface() {
            @Override
            public void onItemClick(Intent intent) {
                startActivity(intent);
            }
        });
        drawerList.setAdapter(drawerListAdapter);

        refreshProfilePicBlurred();
        refreshFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshProfilePicBlurred();
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

    @Override
    public void onHideToolbar() {
        AppBarLayout toolbar = (AppBarLayout)findViewById(R.id.appbarlayout);
        toolbar.animate().setDuration(200).translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    @Override
    public void onShowToolbar() {
        AppBarLayout toolbar = (AppBarLayout)findViewById(R.id.appbarlayout);
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

}
