package fr.epicture.epicture.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Map;

import fr.epicture.epicture.R;
import fr.epicture.epicture.Utils;
import fr.epicture.epicture.api.API;
import fr.epicture.epicture.api.APIAccount;
import fr.epicture.epicture.api.APIManager;
import fr.epicture.epicture.activities.component.AccountOverviewAdapter;
import fr.epicture.epicture.activities.component.APIOverviewAdapter;
import fr.epicture.epicture.api.imgur.Imgur;

public class OAuthLinkerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oauth_linker_activity);

        final WebView webView = ((WebView) findViewById(R.id.myWebView));
        final Spinner oauthSelector = ((Spinner) findViewById(R.id.oauth_selector));
        final ListView accountList = ((ListView) findViewById(R.id.account_list));
        final TextView textView = ((TextView) findViewById(R.id.oauth_text));
        final CheckBox checkBox = ((CheckBox) findViewById(R.id.display_account_box));

        // Checkbox
        checkBox.setChecked(false);
        webView.setVisibility(checkBox.isChecked() ? View.VISIBLE : View.INVISIBLE);
        checkBox.setOnCheckedChangeListener((compoundButton, checked) -> {
            final API api = ((API) oauthSelector.getSelectedItem());
            final String apiName = (api != null ? api.getName() : "");
            if (checked)
            {
                textView.setText("Log in with your " + apiName + " credentials to link your account.");
                webView.setVisibility(View.VISIBLE);
                accountList.setVisibility(View.INVISIBLE);
            }
            else
            {
                textView.setText("Log in with your " + apiName + " credentials to link your account.");
                webView.setVisibility(View.INVISIBLE);
                accountList.setVisibility(View.VISIBLE);
            }
        });

        // Web view
        webView.loadUrl(new Imgur().getAuthorizeLink());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                final Map<String, String> params = Utils.getQueryMap(url);
                final API api = ((API) oauthSelector.getSelectedItem());
                try {
                    final APIAccount apiAccount = api.getType().newAccount(params);
                    api.addAccount(apiAccount);
//                    final Intent intent = new Intent(OAuthLinkerActivity.this, OAuthLinkerActivity.class);
//                    startActivity(intent);// TODO check if it works + send data to redirect to the same view params // + cancel webview load if user is already linked
                }
                catch (InstantiationException e)
                {
                    System.err.println("Error, Account creation failed : not enough data in parameter map.\n\t\t" + params);
                }
                catch (ClassCastException e)
                {
                    System.err.println("Error, Account creation failed : api.addAccount() threw a classCastException");
                }

                for (Map.Entry<String, String> e : params.entrySet())
                    System.out.println("DEBUG key = [" + e.getKey() + "] value = [" + e.getValue() + "]");
                System.out.println("DEBUG XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX : " + url);
            }
        });

        // Spinner
        final APIOverviewAdapter apiOverviewAdapter = new APIOverviewAdapter(this);
        for (API api : APIManager.getApiByType().values())
            apiOverviewAdapter.addItem(api);
        oauthSelector.setAdapter(apiOverviewAdapter);
        oauthSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final API api = apiOverviewAdapter.getItem(i);
                updateDisplay(api);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setSelection(0);
                final API api = apiOverviewAdapter.getItem(0);
                updateDisplay(api);
            }

            public void updateDisplay(API api) {
                final AccountOverviewAdapter accountOverviewAdapter = new AccountOverviewAdapter(OAuthLinkerActivity.this);
                for (APIAccount apiAccount : api.getAccounts())
                    accountOverviewAdapter.addItem(apiAccount);
                accountList.setAdapter(accountOverviewAdapter);
                webView.loadUrl(api.getAuthorizeLink());
            }
        });
    }
}
