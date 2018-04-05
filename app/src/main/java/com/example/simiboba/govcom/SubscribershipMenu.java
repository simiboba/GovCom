package com.example.simiboba.govcom;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.twitter.sdk.android.core.Twitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class SubscribershipMenu extends AppCompatActivity {

    DatabaseReference mRootReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mFeedsReference = mRootReference.child("feeds");
    DatabaseReference mLocationsReference = mRootReference.child("locations");
    ArrayList<Location> locations = new ArrayList<Location>();
    ArrayList<Feed> feeds = new ArrayList<Feed>();

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribership_menu);
        Twitter.initialize(this);

        getLocations();

    }

    private void getLocations() {
        mLocationsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("loc num: database", Long.toString(dataSnapshot.getChildrenCount()));
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Location location = child.getValue(Location.class);
                    locations.add(location);
                }
                Log.i("loc num", Integer.toString(locations.size()));

                // get the listview
                expListView = (ExpandableListView) findViewById(R.id.expandableList);

                // preparing list data
                prepareListData();

                listAdapter = new ExpandableListAdapter(getApplicationContext(), listDataHeader, listDataChild);

                // setting list adapter
                expListView.setAdapter(listAdapter);

                // Listview Group click listener
                listViewGroupClickListener();

                // Listview Group expanded listener
                listViewGroupExpandedListener();

                // Listview Group collapsed listener
                listViewGroupCollapsedListener();

                // Listview on child click listener
                listViewGroupChildClickListener();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());

                Log.e("database", "database information could not be accessed");
            }
        });
    }

    private void getFeeds() {
        mFeedsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot) {
                Log.i("feed num: database", Long.toString(dataSnapshot.getChildrenCount()));
                HashSet<String> screenNames = new HashSet<String>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Feed feed = child.getValue(Feed.class);
                    feeds.add(feed);

                    // for feedview starting purposes simply storing all of the feed name
                    screenNames.add(feed.getScreenName());
                }
                SharedPreferences sp = SubscribershipMenu.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putStringSet("screenNames", screenNames);

                Log.i("feed num", Integer.toString(feeds.size()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                Log.e("database", "database feed information could not be accessed");
            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        Log.i("prepareList: loc num", Integer.toString(locations.size()));
        for (Location location : locations) {
            listDataHeader.add(location.getState());
            listDataChild.put(location.getState(), location.getTowns());

        }

        Log.i("prepared", "list information has been prepared");
    }

    private void listViewGroupClickListener() {
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void listViewGroupExpandedListener() {
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void listViewGroupCollapsedListener() {
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void listViewGroupChildClickListener() {
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }
}
