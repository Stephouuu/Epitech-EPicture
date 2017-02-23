package fr.epicture.epicture.utils;

import android.support.multidex.MultiDexApplication;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Stephane on 14/02/2017.
 */

public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
