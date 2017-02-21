package fr.epicture.epicture.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import fr.epicture.epicture.R;
import fr.epicture.epicture.api.API;
import fr.epicture.epicture.api.APIAccount;
import fr.epicture.epicture.api.APIImageElement;
import fr.epicture.epicture.api.APIManager;
import fr.epicture.epicture.interfaces.LoadBitmapInterface;
import fr.epicture.epicture.interfaces.LoadUserInfoInterface;
import fr.epicture.epicture.utils.DateTimeManager;

public class ImageElementActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE_ELEMENT = "image";

    public static void setImageElement(Intent intent, APIImageElement element) {
        intent.putExtra(EXTRA_IMAGE_ELEMENT, element);
    }

    public static APIImageElement getImageElement(Intent intent) {
        return intent.getParcelableExtra(EXTRA_IMAGE_ELEMENT);
    }

    private ImageView imageView;
    private ProgressBar progressBar;
    private TextView description;
    private TextView date;

    private APIImageElement element;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_element_activity);

        element = getImageElement(getIntent());

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        ((TextView)toolbar.findViewById(R.id.picture_title)).setText(element.title);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        imageView = (ImageView)findViewById(R.id.image);
        progressBar = (ProgressBar)findViewById(R.id.download_progress);
        description = (TextView)findViewById(R.id.description);
        date = (TextView)findViewById(R.id.date);

        refreshImage();
        refreshDescription();
        refreshDate();
        refreshTags();
        refreshOwner();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void refreshDescription() {
        description.setText(Html.fromHtml(element.description));
    }

    private void refreshDate() {
        date.setText(DateTimeManager.getUserFriendlyDateTime(this, element.date));
    }

    private void refreshImage() {
        API api = APIManager.getSelectedAPI();
        progressBar.setVisibility(View.VISIBLE);
        api.loadImage(this, element, new LoadBitmapInterface() {
            @Override
            public void onFinish(Bitmap bitmap) {
                progressBar.setVisibility(View.GONE);
                imageView.setImageBitmap(bitmap);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageElementActivity.this, ImageActivity.class);
                ImageActivity.setImageElement(intent, element);
                startActivity(intent);
            }
        });
    }

    private void refreshTags() {
        ViewGroup container = (ViewGroup)findViewById(R.id.tags_container);
        container.setVisibility(View.VISIBLE);
        String[] tags = element.tags.split(" ");

        container.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (String tag : tags) {
            View view = inflater.inflate(R.layout.tags_preview, container, false);

            ((TextView)view.findViewById(R.id.tags)).setText(tag);
            container.addView(view);
        }
    }

    private void refreshOwner() {
        final TextView ownerName = (TextView)findViewById(R.id.owner_name);
        final ImageView ownerPicture = (ImageView)findViewById(R.id.owner_picture);

        ownerName.setText(element.ownername);

        if (element.ownerid.equals(APIManager.getSelectedAPI().getCurrentAccount().id)) {
            findViewById(R.id.expand).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.expand).setVisibility(View.GONE);
        }

        ownerPicture.setImageResource(R.drawable.placeholder);
        API api = APIManager.getSelectedAPI();
        api.loadUserInformation(this, element.ownerid, new LoadUserInfoInterface() {
            @Override
            public void onFinish(String id, APIAccount result) {
                api.loadUserAvatar(ImageElementActivity.this, result, new LoadBitmapInterface() {
                    @Override
                    public void onFinish(Bitmap bitmap) {
                        ownerPicture.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }
}
