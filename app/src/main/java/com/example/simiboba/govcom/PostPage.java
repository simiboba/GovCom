package com.example.simiboba.govcom;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public class PostPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private HashSet<String> getSavedScreenNames() {
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        return new HashSet(sp.getStringSet("screenNames", null));
    }

    // modeled after:
    // http://www.codehenge.net/2011/05/android-programming-tutorial-a-simple-twitter-feed-reader/
    private ArrayList<Tweet> getTweets(HashSet<String> screenNames) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        for (String screenName : screenNames) {
            int tweetNum = 5;
            String searchURL = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name="
                    + screenName + "&count=" + tweetNum;


        }
        return tweets;
    }
}
