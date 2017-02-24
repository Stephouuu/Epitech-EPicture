package fr.epicture.epicture.api.imgur;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.epicture.epicture.api.APIImageElement;

public class ImgurImageElement extends APIImageElement {
    //private long dateTime;

    //private String tags = "";

    //private String accountUrl;
    //private int accountID;

    private String url;
    private int ups;
    private int downs;
    private int points;
    private int score;

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================

    public ImgurImageElement(JSONObject jsonObject) {
        try {
            setID(jsonObject.getString("id"));
            title = jsonObject.getString("title");
            description = jsonObject.getString("description");
            if (description.equals("null")) {
                description = "";
            }
            date = jsonObject.getLong("datetime");
            ownername = jsonObject.getString("account_url");
            ownerid = jsonObject.getString("account_id");
            url = jsonObject.getString("link");
            commentCount = jsonObject.getInt("comment_count");
            ups = jsonObject.getInt("ups");
            downs = jsonObject.getInt("downs");
            points = jsonObject.getInt("points");
            score = jsonObject.getInt("score");

            tags = "";
            final JSONArray jsonArray = jsonObject.getJSONArray("tags");
            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject tag = ((JSONObject) jsonArray.get(i));
                tags += tag.getString("name");
                if (i + 1 < jsonArray.length())
                    tags += " ";
            }
        } catch (JSONException | ClassCastException e) {
            System.err.println("Error : Unable to convert Json object to ImgurImageElement.\n" + jsonObject.toString());
            e.printStackTrace();
        }
    }

    private ImgurImageElement(Parcel in) {
        ownerid = in.readString();
        title = in.readString();
        description = in.readString();
        date = in.readLong();
        ownername = in.readString();
        url = in.readString();
        commentCount = in.readInt();
        ups = in.readInt();
        downs = in.readInt();
        points = in.readInt();
        score = in.readInt();
        tags = in.readString();
    }

    // ========================================================================
    // METHODS
    // ========================================================================

    public static final Parcelable.Creator<ImgurImageElement> CREATOR = new Parcelable.Creator<ImgurImageElement>() {

        @Override
        public ImgurImageElement createFromParcel(Parcel in) {
            return new ImgurImageElement(in);
        }

        @Override
        public ImgurImageElement[] newArray(int size) {
            return new ImgurImageElement[size];
        }
    };

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public float getWidthSize() {
        return 0;
    }

    @Override
    public float getHeightSize() {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ownerid);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeLong(date);
        parcel.writeString(ownername);
        parcel.writeString(url);
        parcel.writeInt(commentCount);
        parcel.writeInt(ups);
        parcel.writeInt(downs);
        parcel.writeInt(points);
        parcel.writeInt(score);
        parcel.writeString(tags);
    }

    // ------------------------------------------------------------------------
    // Getter / Setter
    // ------------------------------------------------------------------------


    public long getDateTime() {
        return date;
    }

    public void setDateTime(long dateTime) {
        this.date = dateTime;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAccountUrl() {
        return ownername;
    }

    public void setAccountUrl(String accountUrl) {
        this.ownername = accountUrl;
    }

    public String getAccountID() {
        return ownerid;
    }

    public void setAccountID(String accountID) {
        this.ownerid = accountID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public int getDowns() {
        return downs;
    }

    public void setDowns(int downs) {
        this.downs = downs;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
