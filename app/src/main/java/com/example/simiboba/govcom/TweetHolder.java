package com.example.simiboba.govcom;


import java.util.Date;

public class TweetHolder implements Comparable<TweetHolder> {
    private long id;
    private Date createdAt;

    public TweetHolder(long id, Date createdAt) {
      this.id = id;
      this.createdAt = createdAt;

    }

    public long getId() {
        return this.id;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }
    @Override
    public int compareTo(TweetHolder th) {
        return -1 * this.createdAt.compareTo(th.getCreatedAt());
    }
}
