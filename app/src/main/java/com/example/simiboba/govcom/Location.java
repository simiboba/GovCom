package com.example.simiboba.govcom;

import java.util.Arrays;
import java.util.List;

/**
 * Created by simiboba on 14/03/18.
 * This keeps track of each of the locations that
 * are saved in these objects.
 */

public class Location {
    private String state = null;
    private List<String> towns = null;

    public Location(String state, List<String> towns) {
        this.state = state;
        this.towns = towns;
    }
    public Location() {
    }

    public void setState(String state) { this.state = state; }
    public String getState() { return this.state; }

    public void setTowns(List<String> towns) { this.towns = towns; }
    public List<String> getTowns() { return this.towns; }
}
