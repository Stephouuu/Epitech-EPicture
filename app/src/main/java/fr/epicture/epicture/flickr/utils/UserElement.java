package fr.epicture.epicture.flickr.utils;

import org.json.JSONObject;

/**
 * Created by Stephane on 15/02/2017.
 */

public class UserElement {

    public String id;
    public String nsid;
    public boolean ispro;
    public boolean canbuypro;
    public String iconserver;
    public String iconfarm;
    public String username;
    public String realname;
    public String location;
    public String description;

    public UserElement(JSONObject jsonObject) {
        try {
            id = jsonObject.getJSONObject("person").getString("id");
            nsid = jsonObject.getJSONObject("person").getString("nsid");
            ispro = jsonObject.getJSONObject("person").getInt("ispro") == 1;
            canbuypro = jsonObject.getJSONObject("person").getInt("can_buy_pro") == 1;
            iconserver = jsonObject.getJSONObject("person").getString("iconserver");
            iconfarm = jsonObject.getJSONObject("person").getString("iconfarm");
            username = jsonObject.getJSONObject("person").getJSONObject("username").getString("_content");
            realname = jsonObject.getJSONObject("person").getJSONObject("realname").getString("_content");
            location = jsonObject.getJSONObject("person").getJSONObject("location").getString("_content");
            description = jsonObject.getJSONObject("person").getJSONObject("description").getString("_content");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
