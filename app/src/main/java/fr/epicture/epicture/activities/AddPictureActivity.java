package fr.epicture.epicture.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

import fr.epicture.epicture.R;

public class AddPictureActivity extends AppCompatActivity {

    private static final int REQUEST_UPLOAD = 1;

    public static final String EXTRA_PHOTOS = "addpicture.photos";

    @NonNull
    public static String getPhotos(@NonNull Intent intent) {
        String photo = intent.getStringExtra(EXTRA_PHOTOS);
        return photo;
    }

    public static void setPhotos(@NonNull Intent intent, @Nullable String value) {
        intent.putExtra(EXTRA_PHOTOS, value);
    }

    private EditText titleEditText;
    private ImageView imageView;
    private EditText descriptionEditText;

    private String photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_picture_activity);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        titleEditText = (EditText)findViewById(R.id.title);
        imageView = (ImageView)findViewById(R.id.picture_preview);
        descriptionEditText = (EditText)findViewById(R.id.description);

        photo = getPhotos(getIntent());
        refreshPhoto();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_UPLOAD) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_picture_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.send) {
            if (validPhotos() && validTitle() && validDescription()) {
                submitContentRequest();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.app_name)
                        .setMessage(R.string.error_empty_field)
                        .setPositiveButton(R.string.ok, null)
                        .show();
            }
            return true;
        }
        return false;
    }

    private boolean validPhotos() {
        return new File(getPhotos(getIntent())).exists();
    }

    private boolean validTitle() {
        if (titleEditText.getText().toString().trim().length() == 0) {
            titleEditText.setError(getResources().getString(R.string.error_empty));
            return false;
        }
        titleEditText.setError(null);
        return true;
    }

    private boolean validDescription() {
        if (descriptionEditText.getText().toString().trim().length() == 0) {
            descriptionEditText.setError(getResources().getString(R.string.error_empty));
            return false;
        }
        descriptionEditText.setError(null);
        return true;
    }

    private void submitContentRequest() {
        Intent intent = new Intent(this, UploadImageActivity.class);
        UploadImageActivity.setTitle(intent, titleEditText.getText().toString());
        UploadImageActivity.setPhotoPath(intent, photo);
        UploadImageActivity.setDescription(intent, descriptionEditText.getText().toString());
        startActivityForResult(intent, REQUEST_UPLOAD);
    }

    private void refreshPhoto() {
        Bitmap bitmap = BitmapFactory.decodeFile(photo);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }
}
