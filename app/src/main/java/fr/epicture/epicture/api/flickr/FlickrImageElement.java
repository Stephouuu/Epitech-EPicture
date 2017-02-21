package fr.epicture.epicture.api.flickr;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import fr.epicture.epicture.api.APIImageElement;

/**
 * Created by Stephane on 15/02/2017.
 */

public class FlickrImageElement extends APIImageElement {

    private static final String URL = "https://farm%1$s.staticflickr.com/%2$s/%3$s_%4$s%5$s.jpg";

    public String owner;
    public String secret;
    public String server;
    public String farm;
    public boolean isPublic;
    public boolean isFriend;
    public boolean isFamily;

    public FlickrImageElement(JSONObject jsonObject, int size) {
        try {
            setID(jsonObject.getString("id"));
            setSize(size);
            owner = jsonObject.getString("owner");
            secret = jsonObject.getString("secret");
            server = jsonObject.getString("server");
            farm = jsonObject.getString("farm");
            title = jsonObject.getString("title");
            isPublic = jsonObject.getInt("ispublic") != 0;
            isFriend = jsonObject.getInt("isfriend") != 0;
            isFamily = jsonObject.getInt("isfamily") != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FlickrImageElement(String path, String title, String description) {
        super(path, title, description);
    }

    @Override
    public String getURL() {
        String strSize = "";
        if (getSize() == SIZE_THUMBNAIL) {
            strSize = "_t";
        }
        else if (getSize() == SIZE_PREVIEW) {
            strSize = "_z";
        }
        else if (getSize() == SIZE_ORIGINAL) {
            strSize = "_h";
        }
        return String.format(URL, farm, server, getID(), secret, strSize);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getID());
        dest.writeInt(getSize());
        dest.writeString(path);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(owner);
        dest.writeString(secret);
        dest.writeString(server);
        dest.writeString(secret);
        dest.writeString(farm);
        dest.writeInt(isPublic?1:0);
        dest.writeInt(isFriend?1:0);
        dest.writeInt(isFamily?1:0);

    }

    public static final Parcelable.Creator<FlickrImageElement> CREATOR = new Parcelable.Creator<FlickrImageElement>() {

        @Override
        public FlickrImageElement createFromParcel(Parcel in) {
            return new FlickrImageElement(in);
        }

        @Override
        public FlickrImageElement[] newArray(int size) {
            return new FlickrImageElement[size];
        }
    };

    private FlickrImageElement(Parcel in) {
        setID(in.readString());
        setSize(in.readInt());
        path = in.readString();
        title = in.readString();
        description = in.readString();
        owner = in.readString();
        secret = in.readString();
        server = in.readString();
        secret = in.readString();
        farm = in.readString();
        isPublic = in.readInt() == 1;
        isFriend = in.readInt() == 1;
        isFamily = in.readInt() == 1;
    }

}
