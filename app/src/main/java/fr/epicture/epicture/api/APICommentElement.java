package fr.epicture.epicture.api;

/**
 * Created by Stephane on 21/02/2017.
 */

public abstract class APICommentElement {

    public String id;
    public long dateCreate;
    public String authorId;
    public String authorName;
    public String content;

    public abstract String getIconServer();
    public abstract String getIconFarm();
    public abstract String getNSID();

}
