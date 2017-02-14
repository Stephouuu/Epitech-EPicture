package fr.epicture.epicture.flickr.utils;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

//import android.util.Base64;

/**
 * Created by Stephane on 31/01/2017.
 */

public class StaticTools {

    public static int GetCurrentUnixTime() {
        Calendar calendar = Calendar.getInstance();
        return (int)(calendar.getTimeInMillis());
    }

    public static String OAuthEncode(String input) {
        Map<String, String> oauthEncodeMap = new HashMap<>();
        oauthEncodeMap.put("\\*", "%2A");
        oauthEncodeMap.put("\\+", "%20");
        oauthEncodeMap.put("%7E", "~");
        String encoded = "";
        try {
            encoded = URLEncoder.encode(input, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, String> entry : oauthEncodeMap.entrySet()) {
            encoded = encoded.replaceAll(entry.getKey(), entry.getValue());
        }
        return encoded;
    }

}
