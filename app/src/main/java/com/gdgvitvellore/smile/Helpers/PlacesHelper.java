package com.gdgvitvellore.smile.Helpers;

/**
 * Created by nikhil on 17/4/16.
 */
public class PlacesHelper {
    private String placeName;
    private int rate;

    public PlacesHelper(String pn, int r){
        this.setPlaceName(pn);
        this.setRate(r);
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String hotelName) {
        this.placeName = hotelName;
    }
}
