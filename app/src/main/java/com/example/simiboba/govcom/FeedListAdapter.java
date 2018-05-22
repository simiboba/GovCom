package com.example.simiboba.govcom;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FeedListAdapter extends ArrayAdapter<Feed> {
    private Context context;
    private ArrayList<Feed> feedList;

    public FeedListAdapter(Context context, int resource, ArrayList<Feed> feedList) {
        super(context, resource, feedList);
        this.context = context;
        this.feedList = feedList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater
                    = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate(R.layout.feed_list_item, parent, false);
        }
        Feed feed = feedList.get(position);

        TextView feedName = (TextView) convertView.findViewById(R.id.feed_name);
        TextView description = (TextView) convertView.findViewById(R.id.feed_description);
        Switch subscribedTo = (Switch) convertView.findViewById(R.id.subscribed_to);
        if (feed.getSubscribedTo()) {
            subscribedTo.setChecked(true);
        }
        switchClickedListener(subscribedTo, feed);

        feedName.setText(feed.getName());
        description.setText(feed.getDescription());
        subscribedTo.setChecked(feed.getSubscribedTo());


        return convertView;
    }

    private void switchClickedListener(Switch subscribedTo, final Feed feed) {
        subscribedTo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                feed.setSubscribedTo(isChecked);
                SharedPreferences sp = context.getSharedPreferences("userFeeds", Context.MODE_PRIVATE);

                Set<String> fetch = sp.getStringSet("screeNames", null);
                if (isChecked) {
                    fetch.add(feed.getScreenName());
                }
                else {
                    fetch.remove(feed.getScreenName());
                }

                SharedPreferences.Editor editor = sp.edit();
                editor.putStringSet("screenNames", fetch);
                editor.apply();
            }
        });
    }
}
