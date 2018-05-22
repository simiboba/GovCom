package com.example.simiboba.govcom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.tweetui.*;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.List;
import java.util.Set;

import twitter4j.Paging;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class PostPage extends AppCompatActivity {
    private String CONSUMER_KEY = "nbxHdH1EbfPSlmZT3ffwFfQHG";
    private String CONSUMER_SECRET = "n8TUtcIgttzi7Q9IgX9PGAxQ24QRaHLVjn1nhKY5boUlzSdTLF";
    private String ACCESS_TOKEN = "414122069-CsFWyOmpU0cX3oCdxfJwAO1cbHkhFDQIKMNf54L0";
    private String ACCESS_TOKEN_SECRET = "AFE88kDPHkFZS8y8aFBbl4xamooz3Olbzh6cp8pcEEXhf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_layout);;

        TwitterAuthConfig tac = new TwitterAuthConfig(CONSUMER_KEY, CONSUMER_SECRET);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(tac)
                .debug(true)
                .build();
        //finally initialize twitter with created configs
        Twitter.initialize(config);
        TwitterCore.getInstance();
        TweetUi.getInstance();

        // an id for sample tweet: 765523171990704128L
        // download the tweets of each screenName
        downloadTweets(getSavedScreenNames());
    }

    private String[] getSavedScreenNames() {
        SharedPreferences sp = getSharedPreferences("userFeeds", Context.MODE_PRIVATE);
        Set<String> screenNames = sp.getStringSet("screenNames", null);
        for (String screenName : screenNames) {
            Log.i("screenName", screenName);
        }
        String[] screenNameArr = new String[screenNames.size()];
        screenNameArr = screenNames.toArray(screenNameArr);
        return screenNameArr;
    }

    private void downloadTweets(String[] screenNames) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        String[] screenNames1 = {"PrincetonPolice"};

        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadTwitterTask().execute(screenNames1);
        } else {
            Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadTwitterTask extends AsyncTask<String, Void, ArrayList<TweetHolder>> {
        final ProgressDialog dialog = new ProgressDialog(PostPage.this);
        long timer;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait");
            dialog.show();

            timer = System.currentTimeMillis();
        }

        @Override
        protected ArrayList<TweetHolder> doInBackground(String... screenNames) {
            final ArrayList<TweetHolder> results = new ArrayList<TweetHolder>();

//            if (screenNames.length > 0) {
//                ConfigurationBuilder cb = new ConfigurationBuilder();
//                cb.setDebugEnabled(true)
//                        .setOAuthConsumerKey(CONSUMER_KEY)
//                        .setOAuthConsumerSecret(CONSUMER_SECRET)
//                        .setOAuthAccessToken(ACCESS_TOKEN)
//                        .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
//                twitter4j.TwitterFactory tf = new TwitterFactory(cb.build());
//                twitter4j.Twitter twitter = tf.getInstance();
//
//                for (String screenName : screenNames) {
//                    try {
//                        List<twitter4j.Status> statuses;
//
//                        statuses = twitter.getUserTimeline(screenName);
//                        for (int i = 0; i < statuses.size(); i++) {
//                            twitter4j.Status status = statuses.get(i);
//                            TweetHolder th = new TweetHolder(status.getId(), status.getCreatedAt());
//                            results.add(th);
//                        }
//                    } catch (twitter4j.TwitterException te) {
//                        te.printStackTrace();
//                    }
//
//                }
//            }

            if (screenNames.length > 0) {
                ConfigurationBuilder cb = new ConfigurationBuilder();
                cb.setDebugEnabled(true)
                        .setOAuthConsumerKey(CONSUMER_KEY)
                        .setOAuthConsumerSecret(CONSUMER_SECRET)
                        .setOAuthAccessToken(ACCESS_TOKEN)
                        .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
                twitter4j.TwitterFactory tf = new TwitterFactory(cb.build());
                twitter4j.Twitter twitter = tf.getInstance();

                for (String screenName : screenNames) {
                    try {
                        List<twitter4j.Status> statuses;
                        Paging p = new Paging();
                        p.setCount(50);

                        statuses = twitter.getUserTimeline(screenName, p);
                        for (int i = 0; i < statuses.size(); i++) {
                            twitter4j.Status status = statuses.get(i);
                            TweetHolder th = new TweetHolder(status.getId(), status.getCreatedAt());
                            results.add(th);
                        }


                    } catch (twitter4j.TwitterException te) {
                        te.printStackTrace();
                    }

                }
            }
            Collections.sort(results);
            return results;
        }
        @Override
        protected void onPostExecute(ArrayList<TweetHolder> result) {
            final LinearLayout myLayout
                    = (LinearLayout) findViewById(R.id.feed_layout);
            for (TweetHolder th : result) {
                long tweetId = th.getId();
                Log.i("tweetId", Long.toString(tweetId));
                TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
                    @Override
                    public void success(Result<Tweet> result) {
                        myLayout.addView(new TweetView(PostPage.this, result.data,
                                R.style.tw__TweetDarkWithActionsStyle));
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Toast.makeText(PostPage.this, "Twitter Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            double elapsed = (System.currentTimeMillis() - timer) / 1000.0;
            Log.i("elapsed", Double.toString(elapsed));
        }

    }



}
