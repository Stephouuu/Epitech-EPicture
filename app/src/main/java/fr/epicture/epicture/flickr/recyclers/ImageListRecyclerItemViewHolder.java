package fr.epicture.epicture.flickr.recyclers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import fr.epicture.epicture.R;
import fr.epicture.epicture.flickr.interfaces.ImageDiskCacheInterface;
import fr.epicture.epicture.flickr.interfaces.ImageListAdapterInterface;
import fr.epicture.epicture.flickr.utils.ImageDiskCache;
import fr.epicture.epicture.flickr.utils.ImageElement;

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

    public void refreshView(ImageElement imageElement) {

    }

    public void refreshImage(ImageElement imageElement) {
        final ImageView imageView = (ImageView)parent.findViewById(R.id.image);
        imageView.setVisibility(View.GONE);

        new ImageDiskCache().load(activity, imageElement, new ImageDiskCacheInterface() {
            @Override
            public void onFinish(ImageElement imageElement, Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            }
        });
    }

}
