package fr.epicture.epicture.flickr.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Stephane on 15/02/2017.
 */

public class TokenAccess implements Parcelable {

    public String fullname;
    public String token;
    public String tokenSecret;
    public String nsid;
    public String username;

    public TokenAccess() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullname);
        dest.writeString(token);
        dest.writeString(tokenSecret);
        dest.writeString(nsid);
        dest.writeString(username);
    }

    public static final Parcelable.Creator<TokenAccess> CREATOR = new Parcelable.Creator<TokenAccess>() {

        @Override
        public TokenAccess createFromParcel(Parcel in) {
            return new TokenAccess(in);
        }

        @Override
        public TokenAccess[] newArray(int size) {
            return new TokenAccess[size];
        }
    };

    private TokenAccess(Parcel in) {
        fullname = in.readString();
        token = in.readString();
        tokenSecret = in.readString();
        nsid = in.readString();
        username = in.readString();
    }
}
