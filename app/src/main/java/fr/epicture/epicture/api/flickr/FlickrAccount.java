package fr.epicture.epicture.api.flickr;

import org.json.JSONObject;

import fr.epicture.epicture.api.APIAccount;
import fr.epicture.epicture.api.flickr.modele.TokenAccess;

/**
 * Created by Stephane on 15/02/2017.
 */

public class FlickrAccount extends APIAccount {

    public int dbid;
    public String nsid;
    public String token;
    public String tokenSecret;
    public String fullname;
    public String iconserver;
    public String iconfarm;
    public String location;
    public String description;


    //public String fullname;
    //public String username;

    public FlickrAccount(TokenAccess tokenAccess) {
        this.fullname = tokenAccess.fullname;
        this.token = tokenAccess.token;
        this.tokenSecret = tokenAccess.tokenSecret;
        this.nsid = tokenAccess.nsid;
        this.username = tokenAccess.username;
    }

    public void setData(JSONObject jsonObject) {
        try {
            id = jsonObject.getJSONObject("person").getString("id");
            username = jsonObject.getJSONObject("person").getJSONObject("username").getString("_content");

            nsid = jsonObject.getJSONObject("person").getString("nsid");
            iconserver = jsonObject.getJSONObject("person").getString("iconserver");
            iconfarm = jsonObject.getJSONObject("person").getString("iconfarm");
            realname = jsonObject.getJSONObject("person").getJSONObject("realname").getString("_content");
            location = jsonObject.getJSONObject("person").getJSONObject("location").getString("_content");
            description = jsonObject.getJSONObject("person").getJSONObject("description").getString("_content");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
