package fr.epicture.epicture.interfaces;

import java.util.List;

import fr.epicture.epicture.api.APIImageElement;

/**
 * Created by Stephane on 18/02/2017.
 */

public interface LoadImageElementInterface {

    void onFinish(List<APIImageElement> datas, boolean error);

}
