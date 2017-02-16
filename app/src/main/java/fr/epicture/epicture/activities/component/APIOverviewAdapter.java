package fr.epicture.epicture.activities.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.epicture.epicture.R;
import fr.epicture.epicture.api.API;

public class APIOverviewAdapter extends BaseAdapter {

    // ========================================================================
    // FIELDS
    // ========================================================================

    private final List<API> apis = new ArrayList<>();
    private final Context context;

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================

    public APIOverviewAdapter(Context context) {
        super();
        this.context = context;
    }

    // ========================================================================
    // METHODS
    // ========================================================================

    public void addItem(API api) {
        apis.add(api);
    }

    @Override
    public int getCount() {
        return apis.size();
    }

    @Override
    public API getItem(int i) {
        return apis.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        final View view = (convertView != null) ? convertView : LayoutInflater.from(context).inflate(R.layout.api_overview, viewGroup, false);
        final API api = getItem(i);
        final TextView textView = ((TextView) view.findViewById(R.id.api_name));
        textView.setText(api.getName());
        return view;
    }
}
