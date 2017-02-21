package fr.epicture.epicture.interfaces;

import fr.epicture.epicture.api.APIImageElement;

/**
 * Created by Stephane on 15/02/2017.
 */

public interface ImageListInterface extends RetractableToolbarInterface {

    void onRequestImageList(int page, String search, String userID);
    void onImageClick(APIImageElement element);

}
