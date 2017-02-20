package fr.epicture.epicture.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import fr.epicture.epicture.R;
import fr.epicture.epicture.adapters.APIOverviewAdapter;
import fr.epicture.epicture.adapters.AccountOverviewAdapter;
import fr.epicture.epicture.api.APIAccount;
import fr.epicture.epicture.api.APIManager;
import fr.epicture.epicture.api.flickr.Flickr;
import fr.epicture.epicture.api.imgur.Imgur;
import fr.epicture.epicture.interfaces.AuthentificationInterface;
import fr.epicture.epicture.interfaces.LoadUserInfoInterface;

public class StartUpActivity extends AppCompatActivity implements AuthentificationInterface {

    private static final int REQUEST_USER_PERMISSION = 1;

    private ListView accountListView;
    private Spinner apiSpinner;
    private Button addAccountButton;
    private TextView noAccountTextView;

    private APIOverviewAdapter apiAdapter;
    private AccountOverviewAdapter accountAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup_activity);

        APIManager.addAPI(new Flickr(getApplicationContext()));
        APIManager.addAPI(new Imgur());

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        apiSpinner = ((Spinner) findViewById(R.id.oauth_selector));
        accountListView = ((ListView) findViewById(R.id.account_list));
        addAccountButton = (Button) findViewById(R.id.button_connect);
        noAccountTextView = (TextView) findViewById(R.id.account_no_item);

        apiAdapter = new APIOverviewAdapter(this);
        apiAdapter.addAll(APIManager.getAPIList());
        apiSpinner.setAdapter(apiAdapter);

        accountAdapter = new AccountOverviewAdapter(StartUpActivity.this, new AccountOverviewAdapter.Listener() {
            @Override
            public void onAccountClick(APIAccount account) {
                APIManager.getSelectedAPI().setCurrentAccount(account);

                Intent intent = new Intent(StartUpActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        accountListView.setAdapter(accountAdapter);

        apiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                APIManager.setSelectedAPI(apiAdapter.getItem(i));
                refreshUserInfos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setSelection(0);
                APIManager.setSelectedAPI(apiAdapter.getItem(0));
            }
        });

        addAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIManager.getSelectedAPI().setAuthentificationListener(StartUpActivity.this);
                APIManager.getSelectedAPI().authentification(StartUpActivity.this);
            }
        });

        APIManager.setSelectedAPI(APIManager.getAPIByIndex(0));
    }

    @Override
    public void onBackPressed() {
    }

    private void refreshAccountList() {
        accountAdapter.addAll(APIManager.getSelectedAPI().getAccounts());
        if (accountAdapter.getCount() == 0) {
            noAccountTextView.setVisibility(View.VISIBLE);
            accountListView.setVisibility(View.GONE);
        } else {
            noAccountTextView.setVisibility(View.GONE);
            accountListView.setVisibility(View.VISIBLE);
        }
    }

    private void refreshUserInfos() {
        refreshAccountList();
        APIManager.getSelectedAPI().loadUserInformation(this, new LoadUserInfoInterface() {
            @Override
            public void onFinish(String id, APIAccount result) {
                accountAdapter.set(id, result);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_USER_PERMISSION) {
            if (resultCode == RESULT_OK) {
                String urlResponse = UserPermissionActivity.getExtraUrlResponse(data);
                APIManager.getSelectedAPI().afterUserPermissionRequest(this, urlResponse);
            }
        }
    }

    @Override
    public void onUserPermissionRequest(String url, String grantWord) {
        Intent intent = new Intent(this, UserPermissionActivity.class);
        UserPermissionActivity.setExtraUrl(intent, url);
        UserPermissionActivity.setExtraSearchfor(intent, grantWord);
        startActivityForResult(intent, REQUEST_USER_PERMISSION);
    }

    @Override
    public void onUserPermissionGranted() {
        refreshUserInfos();
    }

}
