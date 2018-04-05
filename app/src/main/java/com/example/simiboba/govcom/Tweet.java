package com.example.simiboba.govcom;

import java.util.Calendar;

public class Tweet {
    private long tweetID;
    private Calendar dateAdded;

    public Tweet(long tweetID, Calendar dateAdded) {
        this.tweetID = tweetID;
        this.dateAdded = dateAdded;
    }

    public long getTweetID() { return tweetID; }
    public Calendar getDateAdded() { return dateAdded; }

    public int compareTo(Tweet other) {
        return dateAdded.compareTo(other.getDateAdded());
    }
}
