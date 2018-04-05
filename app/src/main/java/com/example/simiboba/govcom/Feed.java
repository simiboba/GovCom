package com.example.simiboba.govcom;

import java.util.List;

/**
 * Created by simiboba on 12/03/18.
 * Each of these Feed objects save each of the
 * information sections of a feed.
 */

public class Feed {
    private String name = null;
    private List<String> states = null;
    private List<String>  towns = null;
    private Boolean isTwitter = new Boolean(false);
    private Boolean isFacebook = new Boolean(false);
    private String description = null;
    private String screenName = null;

    public Feed(String name, List<String> states, List<String> towns, Boolean isTwitter,
                Boolean isFacebook, String description, String screenName) {
        this.name = name;
        this.states = states;
        this.towns = towns;
        this.isTwitter = isTwitter;
        this.isFacebook = isFacebook;
        this.description = description;
        this.screenName = screenName;
    }

    public Feed() {
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getStates() { return states; }
    public void setStates(List<String> states) { this.states = states; }

    public List<String>  getTowns() { return towns; }
    public void setTowns(List<String> towns) { this.towns = towns; }

    public Boolean getIsTwitter() { return isTwitter; }
    public void setIsTwitter(boolean isTwitter) { this.isTwitter = new Boolean(isTwitter); }

    public Boolean getIsFacebook() { return isFacebook; }
    public void setIsFacebook(boolean isFacebook) { this.isFacebook = new Boolean(isFacebook); }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getScreenName() { return screenName; }
    public void setScreenName( String screenName ) { this.screenName = screenName; }

}
