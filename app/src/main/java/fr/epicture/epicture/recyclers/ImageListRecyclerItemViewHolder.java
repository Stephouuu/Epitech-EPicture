package fr.epicture.epicture.recyclers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

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

    public void refreshView(APIImageElement imageElement, boolean isFooter) {
        Log.i("footer", isFooter + "");
        if (isFooter) {
            parent.findViewById(R.id.footer).setVisibility(View.VISIBLE);
        } else {
            parent.findViewById(R.id.footer).setVisibility(View.GONE);
        }
    }

    public void refreshImage(APIImageElement element) {
        final ImageView imageView = (ImageView)parent.findViewById(R.id.image);
        final ProgressBar progressBar = (ProgressBar)parent.findViewById(R.id.download_progress);

        imageView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        API api = APIManager.getSelectedAPI();
        api.loadImage(activity, element, new LoadBitmapInterface() {
            @Override
            public void onFinish(Bitmap bitmap){
                imageView.setImageBitmap(bitmap);
                progressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
            }
        });
    }

}
