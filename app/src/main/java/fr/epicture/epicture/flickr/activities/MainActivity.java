package fr.epicture.epicture.flickr.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.epicture.epicture.R;
import fr.epicture.epicture.flickr.utils.AuthentificationManager;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        AuthentificationManager am = new AuthentificationManager(this);
        am.authentify();
    }
}
