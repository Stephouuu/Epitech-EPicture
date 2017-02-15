package fr.epicture.epicture.api.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.epicture.epicture.R;
import fr.epicture.epicture.api.APIAccount;

public class AccountOverviewAdapter extends BaseAdapter {

    private final List<APIAccount> apis = new ArrayList<>();
    private final Context context;

    public AccountOverviewAdapter(Context context) {
        super();
        this.context = context;
    }

    public void addItem(APIAccount api) {
        apis.add(api);
    }

    @Override
    public int getCount() {
        return apis.size();
    }

    @Override
    public APIAccount getItem(int i) {
        return apis.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final View view = (convertView != null) ? convertView : LayoutInflater.from(context).inflate(R.layout.account_overview, viewGroup, false);
        final APIAccount apiAccount = getItem(i);
        final TextView textView = ((TextView) view.findViewById(R.id.account_name));
        textView.setText(apiAccount.getUsername());
        return view;
    }

}
