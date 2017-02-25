package fr.epicture.epicture.api;

import android.content.Context;

import java.util.Collection;

import fr.epicture.epicture.interfaces.AddCommentInterface;
import fr.epicture.epicture.interfaces.AuthentificationInterface;
import fr.epicture.epicture.interfaces.LoadBitmapInterface;
import fr.epicture.epicture.interfaces.LoadCommentElementInterface;
import fr.epicture.epicture.interfaces.LoadImageElementInterface;
import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.interfaces.LoadUserInfoInterface;
import fr.epicture.epicture.interfaces.PhotoIsInFavoritesInterface;

/**
 * API.java
 * Interface class for API abstraction
 */
public interface API {

    /**
     * Returns a drawable id corresponding to the logo of the API
     *
     * @return the id of the drawable logo
     */
    int getLogo();

    /**
     * Returns a String containing the name of the API
     *
     * @return the name of the API
     */
    String getName();

    /**
     * Sets the AuthentificationInterface of the API
     *
     * @param listener Authentification Interface of the API
     */
    void setAuthentificationListener(AuthentificationInterface listener);

    /**
     * Sends an authentication request to the API
     *
     * @param context Android context
     */
    void authentification(Context context);

    /**
     * Retrieves and stores user information after authentication
     *
     * @param context Android context
     * @param urlResponse urlResponse
     */
    void afterUserPermissionRequest(Context context, String urlResponse);

    /**
     * Retrieves previously stored user information
     *
     * @param context Android context
     * @param callback LoadUserInfoInterface callback
     */
    void loadUserInformation(Context context, LoadUserInfoInterface callback);

    /**
     * Retrieves user information
     *
     * @param context Android context
     * @param id the ID of the user
     * @param callback LoadUserInfoInterface callback
     */
    void loadUserInformation(Context context, String id, LoadUserInfoInterface callback);

    /**
     * Loads the avatar image of the account
     *
     * @param context Android context
     * @param account APIAccount object containing account information
     * @param callback LoadBitmapInterface callback
     */
    void loadUserAvatar(Context context, APIAccount account, LoadBitmapInterface callback);

    /**
     * Loads user avatar for a comment
     *
     * @param context Android context
     * @param commentElement comment
     * @param callback LoadBitmapInterface callback
     */
    void loadUserAvatar(Context context, APICommentElement commentElement, LoadBitmapInterface callback);

    /**
     * Loads an image from disk cache and passes it to the callback
     *
     * @param context Android context
     * @param element API image element
     * @param callback LoadBitmapInterface callback
     */
    void loadImage(Context context, APIImageElement element, LoadBitmapInterface callback);

    /**
     * Uploads picture at path to the user account with title, description and tags
     *
     * @param context Android context
     * @param user user account to upload the image to
     * @param path path of the image
     * @param title title of the image
     * @param description description of the image
     * @param tags tags of the image
     * @param callback LoadTextInterface callback
     */
    void uploadImage(Context context, APIAccount user, String path, String title, String description, String tags, LoadTextInterface callback);

    /**
     * Sends a request to delete an image
     *
     * @param context Android context
     * @param photoid id of the image
     * @param userid user id of the owner
     * @param callback LoadTextInterface callback
     */
    void deletePhoto(Context context, String photoid, String userid, LoadTextInterface callback);

    /**
     * Retrieves a list of interesting photos of the day at a given page
     *
     * @param context Android context
     * @param page page of the list
     * @param callback LoadImageElementInterface callback
     */
    void getInterestingnessList(Context context, int page, LoadImageElementInterface callback);

    /**
     * Retrieves the pictures of the currently authenticated user at a given page
     *
     * @param context Android context
     * @param page page of the list
     * @param callback LoadImageElementInterface callback
     */
    void getMyPictures(Context context, int page, LoadImageElementInterface callback);

    /**
     * Retrieves the results of the search query
     *
     * @param context Android context
     * @param search search query
     * @param userid Id of the user who executes the search
     * @param page page of the results
     * @param callback LoadImageElementInterface callback
     */
    void search(Context context, String search, String userid, int page, LoadImageElementInterface callback);

    /**
     * Adds a comment to a given photo
     *
     * @param context Android context
     * @param userid user posting the comment
     * @param photoid Id of the commented photo
     * @param comment content of the comment
     * @param callback AddCommentInterface callback
     */
    void addComment(Context context, String userid, String photoid, String comment, AddCommentInterface callback);

    /**
     * Retrieves comments form a given picture
     *
     * @param context Android context
     * @param userid user retrieving the comments
     * @param photoid id of the commented photo
     * @param callback LoadCommentInterface callback
     */
    void getComments(Context context, String userid, String photoid, LoadCommentElementInterface callback);

    /**
     * Adds a photo to favorites
     *
     * @param context Android context
     * @param userid user to add favorite to
     * @param photoid id of the photo
     * @param callback LoadTextInterface callback
     */
    void addFavorite(Context context, String userid, String photoid, LoadTextInterface callback);

    /**
     * Removes a photo from favorites
     *
     * @param context Android context
     * @param userid user to remove the favorite from
     * @param photoid id of the photo
     * @param callback LoadTextInterface callback
     */
    void deleteFavorite(Context context, String userid, String photoid, LoadTextInterface callback);

    /**
     * Checks if a photo is in the favorites of the user
     *
     * @param context Android context
     * @param userid user whose favorites are checked
     * @param photoid photo to check
     * @param callback callback
     */
    void isFavorite(Context context, String userid, String photoid, PhotoIsInFavoritesInterface callback);

    /**
     * Retrieves a list of the favorites of the user at a given page
     *
     * @param context Android context
     * @param userid user id to retrieve the favorites from
     * @param page page of the list
     * @param callback callback
     */
    void getFavorites(Context context, String userid, int page, LoadImageElementInterface callback);

    /**
     * Sets the current API accout to be used
     *
     * @param account account to be used
     */
    void setCurrentAccount(APIAccount account);

    /**
     * Returns the current API account
     *
     * @return current API account
     */
    APIAccount getCurrentAccount();

    /**
     * Returns all accounts for the API
     *
     * @return Api accounts collection
     */
    Collection<APIAccount> getAccounts();
}
