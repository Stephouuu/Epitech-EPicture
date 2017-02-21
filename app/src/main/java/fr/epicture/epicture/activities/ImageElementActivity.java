package fr.epicture.epicture.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import fr.epicture.epicture.R;
import fr.epicture.epicture.api.API;
import fr.epicture.epicture.api.APIImageElement;
import fr.epicture.epicture.api.APIManager;
import fr.epicture.epicture.interfaces.LoadBitmapInterface;

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

    private APIImageElement element;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_element_activity);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        TextView title = (TextView)toolbar.findViewById(R.id.picture_title);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        element = getImageElement(getIntent());

        imageView = (ImageView)findViewById(R.id.image);
        progressBar = (ProgressBar)findViewById(R.id.download_progress);

        title.setText(element.title);
        refreshImage();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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

}
