package fr.epicture.epicture.recyclers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import fr.epicture.epicture.R;
import fr.epicture.epicture.api.API;
import fr.epicture.epicture.api.APIImageElement;
import fr.epicture.epicture.api.APIManager;
import fr.epicture.epicture.interfaces.ImageListAdapterInterface;
import fr.epicture.epicture.interfaces.LoadBitmapInterface;

/**
 * Created by Stephane on 15/02/2017.
 */

public class ImageListRecyclerItemViewHolder extends RecyclerView.ViewHolder {

    private Activity activity;
    private View parent;
    private ImageListAdapterInterface listener;

    private ImageListRecyclerItemViewHolder(Activity activity, View parent, ImageListAdapterInterface listener) {
        super(parent);
        this.activity = activity;
        this.parent = parent;
        this.listener = listener;
    }

    public static ImageListRecyclerItemViewHolder newInstance(Activity activity, View parent, ImageListAdapterInterface listener) {
        return (new ImageListRecyclerItemViewHolder(activity, parent, listener));
    }

    public void refreshView(APIImageElement imageElement) {

    }

    public void refreshImage(APIImageElement element) {
        final ImageView imageView = (ImageView)parent.findViewById(R.id.image);
        imageView.setVisibility(View.GONE);

        API api = APIManager.getSelectedAPI();
        api.loadImage(activity, element, new LoadBitmapInterface() {
            @Override
            public void onFinish(Bitmap bitmap){
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            }
        });
    }

}
