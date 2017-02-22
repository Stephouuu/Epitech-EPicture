package fr.epicture.epicture.recyclers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import fr.epicture.epicture.R;
import fr.epicture.epicture.api.API;
import fr.epicture.epicture.api.APIAccount;
import fr.epicture.epicture.api.APIImageElement;
import fr.epicture.epicture.api.APIManager;
import fr.epicture.epicture.interfaces.ImageListAdapterInterface;
import fr.epicture.epicture.interfaces.LoadBitmapInterface;
import fr.epicture.epicture.interfaces.LoadTextInterface;
import fr.epicture.epicture.interfaces.LoadUserInfoInterface;
import fr.epicture.epicture.utils.DateTimeManager;

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
        if (isFooter) {
            parent.findViewById(R.id.footer).setVisibility(View.VISIBLE);
        } else {
            parent.findViewById(R.id.footer).setVisibility(View.GONE);
        }

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onImageClick(imageElement);
            }
        });
    }

    public void refreshImage(APIImageElement element) {
        final ImageView imageView = (ImageView)parent.findViewById(R.id.image);
        final View imageContainer = parent.findViewById(R.id.image_container);
        final ProgressBar progressBar = (ProgressBar)parent.findViewById(R.id.download_progress);
        final float height = element.getHeightSize();

        imageView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        DisplayMetrics screen = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(screen);

        if (height > 0) {
            imageContainer.getLayoutParams().width = screen.widthPixels;
            imageContainer.getLayoutParams().height = (int)height;
            imageContainer.requestLayout();
        }

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

    public void refreshDescription(APIImageElement element) {
        final TextView description = (TextView) parent.findViewById(R.id.description);

        description.setEllipsize(TextUtils.TruncateAt.END);
        description.setMaxLines(3);
        description.setText(Html.fromHtml(element.description));
    }

    public void refreshDate(APIImageElement element) {
        final TextView date = (TextView)parent.findViewById(R.id.date);
        date.setText(DateTimeManager.getUserFriendlyDateTime(activity, element.date));
    }

    public void refreshOwner(APIImageElement element) {
        final TextView ownerName = (TextView)parent.findViewById(R.id.owner_name);
        final ImageView ownerPicture = (ImageView)parent.findViewById(R.id.owner_picture);

        ownerName.setText(element.ownername);

        parent.findViewById(R.id.header_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API api = APIManager.getSelectedAPI();
                api.deletePhoto(activity, element.getID(), api.getCurrentAccount().getID(), new LoadTextInterface() {
                    @Override
                    public void onFinish(String text) {
                        listener.onImageDelete(element);
                    }
                });
            }
        });

        if (element.ownerid.equals(APIManager.getSelectedAPI().getCurrentAccount().id)) {
            parent.findViewById(R.id.expand).setVisibility(View.VISIBLE);
        } else {
            parent.findViewById(R.id.expand).setVisibility(View.GONE);
        }

        ownerPicture.setImageResource(R.drawable.placeholder);
        API api = APIManager.getSelectedAPI();
        api.loadUserInformation(activity, element.ownerid, new LoadUserInfoInterface() {
            @Override
            public void onFinish(String id, APIAccount result) {
                api.loadUserAvatar(activity, result, new LoadBitmapInterface() {
                    @Override
                    public void onFinish(Bitmap bitmap) {
                        ownerPicture.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

}
