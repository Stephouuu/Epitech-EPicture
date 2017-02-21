package fr.epicture.epicture.api.flickr.requests;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.File;

import fr.epicture.epicture.api.flickr.FlickrAccount;
import fr.epicture.epicture.api.flickr.FlickrClient;
import fr.epicture.epicture.api.flickr.FlickrImageElement;
import fr.epicture.epicture.api.flickr.utils.FlickrUtils;
import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.requests.UploadRequest;
import fr.epicture.epicture.utils.RequestIdentifierGenerator;
import fr.epicture.epicture.utils.StaticTools;

/**
 * Created by Stephane on 19/02/2017.
 */

public class UploadPictureRequest extends UploadRequest {

    public static final String URL = "https://up.flickr.com/services/upload/";

    private FlickrAccount user;
    private FlickrImageElement element;

    public UploadPictureRequest(@NonNull Context context, FlickrAccount user, FlickrImageElement element,
                                LoadTextInterface listener) {
        super(context, URL, listener);

        this.user = user;
        this.element = element;

        addHeader();
        addParam();

        execute();
    }

    private void addHeader() {
        String random = RequestIdentifierGenerator.Generate();
        long unixTime = StaticTools.GetCurrentUnixTime();

        addHeader("Authorization", "OAuth oauth_nonce=\"" + random + "\""
                + ",oauth_timestamp=\"" + unixTime + "\""
                + ",oauth_consumer_key=\"" + FlickrClient.CONSUMER_KEY + "\""
                + ",oauth_signature_method=\"HMAC-SHA1\""
                + ",oauth_signature=\"" + getSignature(random, unixTime) + "\""
                + ",oauth_token=\"" + user.token + "\"");
    }

    private void addParam() {
        if (element.title != null && !element.title.isEmpty()) {
            addParam(new ParamBody("title", element.title));
        }
        if (element.description != null && !element.description.isEmpty()) {
            addParam(new ParamBody("description", element.description));
        }

        byte[] bytes = this.createByteArray(element.path);
        addParam(new ParamFile("img.jpg", "photo", bytes, bytes.length, "image/jpeg"));
    }

    private String getSignature(String random, long unixTime) {
        String part1 = "POST";
        String part2 = URL;

        String[] params = new String[] {
                "oauth_nonce=" + random,
                "oauth_consumer_key=" + FlickrClient.CONSUMER_KEY,
                "oauth_timestamp=" + unixTime,
                "oauth_signature_method=HMAC-SHA1",
                "oauth_token=" + user.token,
                "title=" + element.title,
                "description=" + element.description
        };

        return FlickrUtils.getPOSTSignature(part1, part2, params, user.tokenSecret);
    }

    private byte[] createByteArray(String path) {
        File file = new File(path);

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        /*float height = (float) bitmap.getHeight();
        float width = (float) bitmap.getWidth();
        float ratio = height / width;

        if (ratio > 1) {
            if (bitmap.getHeight() > 1440) {
                height = 1440.0f;
                width = 1440.0f / ratio;
            }
        } else {
            if (bitmap.getWidth() > 1440) {
                width = 1440.0f;
                height = 1440.0f * ratio;
            }
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) width, (int) height, true);*/

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();

        return byteArray;
    }

}
