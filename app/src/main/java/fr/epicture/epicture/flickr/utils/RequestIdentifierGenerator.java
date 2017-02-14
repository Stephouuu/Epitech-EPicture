package fr.epicture.epicture.flickr.utils;

import android.util.Log;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Stephane on 31/01/2017.
 */

public class RequestIdentifierGenerator {

    private final static SecureRandom random_ = new SecureRandom();

    public static String Generate() {
        String id = new BigInteger(130, random_).toString(32);
        Log.i("IdentifierGenerator", id);
        return (id);
    }

}
