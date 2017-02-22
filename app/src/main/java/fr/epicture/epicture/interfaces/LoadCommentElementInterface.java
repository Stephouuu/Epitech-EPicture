package fr.epicture.epicture.interfaces;

import java.util.List;

import fr.epicture.epicture.api.APICommentElement;

/**
 * Created by Stephane on 21/02/2017.
 */

public interface LoadCommentElementInterface {

    void onFinish(List<APICommentElement> datas, boolean error);

}
