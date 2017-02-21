package fr.epicture.epicture.api.imgur;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.epicture.epicture.api.APIAccount;
import fr.epicture.epicture.api.APIImageElement;
import fr.epicture.epicture.api.imgur.requests.GalleryRequest;
import fr.epicture.epicture.api.imgur.requests.RefreshTokenRequest;
import fr.epicture.epicture.api.imgur.requests.UserInformationRequest;
import fr.epicture.epicture.api.imgur.utils.ImgurUtils;
import fr.epicture.epicture.interfaces.LoadImageElementInterface;

public class ImgurAccount extends APIAccount {

    // ========================================================================
    // FIELDS
    // ========================================================================

    private AccessToken accessToken;
    private String refreshToken;

    private String bio;
    private double reputation;
    private long creationDate;


    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================

    public ImgurAccount(Map<String, String> params) throws InstantiationException {
        super(params.get("account_id"), params.get("account_username"));
        new AccessToken(params.get("access_token"), Long.parseLong(params.get("expires_in")), ImgurUtils.getTime());
    }

    // ========================================================================
    // METHODS
    // ========================================================================

    public static void getMainGallery(Context context, int page, LoadImageElementInterface loadImageElementInterface)
    {
        new GalleryRequest(context, "hot", "viral", page, text -> {
            try {
                final JSONArray jsonArray = new JSONObject(text).getJSONArray("data");
                final List<APIImageElement> imgurImageElements = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++)
                    imgurImageElements.add(new ImgurImageElement(((JSONObject) jsonArray.get(i))));
                loadImageElementInterface.onFinish(imgurImageElements, !imgurImageElements.isEmpty());
            }
            catch (JSONException | ClassCastException e)
            {
                System.err.println("Error : Unable to convert request data to json.");
                e.printStackTrace();
            }
        }).execute();
    }

    // ------------------------------------------------------------------------
    // Data update
    // ------------------------------------------------------------------------

    public void updateAccessToken(Context context) {
        new RefreshTokenRequest(context, refreshToken, text -> {
            try {
                final JSONObject jsonObject = new JSONObject(text);
                accessToken.update(
                        jsonObject.getString("access_token"),
                        ImgurUtils.getTime(),
                        jsonObject.getLong("expires_in")
                );
            } catch (JSONException e) {
                System.err.println("Failed to update access token.");
                e.printStackTrace();
            }
        }).execute();
    }

    public void updateInformation(Context context) {
        new UserInformationRequest(context, username, accessToken.getAccessToken(), text -> {
            try {
                JSONObject jsonObject = new JSONObject(text);
                if (jsonObject.getBoolean("success")) {
                    jsonObject = ((JSONObject) jsonObject.get("data"));
                    id = String.valueOf(jsonObject.getInt("access_token"));
                    username = jsonObject.getString("url");
                    bio = jsonObject.getString("bio");
                    reputation = jsonObject.getDouble("reputation");
                    creationDate = jsonObject.getLong("created");
                }
            } catch (JSONException e) {
                System.err.println("Failed to update user information.");
                e.printStackTrace();
            }
        }).execute();
    }

    // ------------------------------------------------------------------------
    // Getter / Setter
    // ------------------------------------------------------------------------

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getNSID() {
        return null;
    }

    @Override
    public String getIconServer() {
        return null;
    }

    @Override
    public String getFarm() {
        return null;
    }
}
