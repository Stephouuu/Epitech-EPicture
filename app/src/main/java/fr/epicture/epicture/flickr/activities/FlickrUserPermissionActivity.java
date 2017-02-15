package fr.epicture.epicture.flickr.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import fr.epicture.epicture.R;
import fr.epicture.epicture.flickr.model.TokenRequest;

public class FlickrUserPermissionActivity extends AppCompatActivity {

    public static final String EXTRA_TOKENREQUEST = "token.request";

    public static void setExtraTokenrequest(Intent intent, TokenRequest tokenRequest) {
        intent.putExtra(EXTRA_TOKENREQUEST, tokenRequest);
    }

    public static TokenRequest getExtraTokenrequest(Intent intent) {
        return intent.getParcelableExtra(EXTRA_TOKENREQUEST);
    }

    private TokenRequest tokenRequest;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flickr_user_permission_activity);

        tokenRequest = getExtraTokenrequest(getIntent());

        webView = (WebView)findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        start();
    }

    private void start() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains("oauth_verifier")) {
                    tokenRequest.verifier = Uri.parse(url).getQueryParameter("oauth_verifier");
                    getIntent().putExtra(EXTRA_TOKENREQUEST, tokenRequest);
                    setResult(RESULT_OK, getIntent());
                    finish();
                }
            }
        });
        webView.loadUrl("https://www.flickr.com/services/oauth/authorize?oauth_token=" + tokenRequest.token
            + "&perms=delete");
    }
}
