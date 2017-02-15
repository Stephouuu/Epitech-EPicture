package fr.epicture.epicture.flickr.utils;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

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

    public static String getSignature(String data, String key) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
        Mac macInstance = Mac.getInstance("HmacSHA1");
        macInstance.init(keySpec);
        byte[] signedBytes = macInstance.doFinal(data.getBytes());
        return (new String(Base64.encodeBase64(signedBytes)));
    }

    public static boolean isJSON(String content) {
        try {
            new JSONObject(content);
        } catch (Exception e) {
            try {
                new JSONArray(content);
            } catch (Exception ex) {
                return false;
            }
        }
        return true;
    }

    public static boolean saveBitmapToJpegFile(@NonNull Bitmap bitmap, @NonNull File target) {
        try {
            target.getParentFile().mkdirs();
            FileOutputStream output = new FileOutputStream(target);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, output);
            output.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
