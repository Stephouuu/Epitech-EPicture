package fr.epicture.epicture.requests;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.epicture.epicture.asynctasks.RequestAsyncTask;
import fr.epicture.epicture.interfaces.LoadTextInterface;

/**
 * Created by Stephane on 19/02/2017.
 */

public class UploadRequest extends RequestAsyncTask {

    private String url;
    private List<Object> params;
    private Map<String, String> header;
    private LoadTextInterface listener;

    protected UploadRequest(@NonNull Context context, String url, LoadTextInterface listener) {
        super(context);
        this.url = url;
        this.listener = listener;

        params = new ArrayList<>();
        header = new HashMap<>();
    }

    protected void addParam(Object object) {
        params.add(object);
    }

    protected void addHeader(String key, String value) {
        header.put(key, value);
    }

    @Override
    @Nullable
    protected Void doInBackground(@Nullable Void... params) {
        try {
            POSTMultipart(url, this.params, header);

            Log.i("uploadimagerequest", String.format("POST %s", url));
            Log.i("uploadimagerequest", String.format("BODY %s", response));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (null);
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        try {
            listener.onFinish(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
