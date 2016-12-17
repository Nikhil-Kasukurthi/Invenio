package com.gdgvitvellore.smile.Helpers;

/**
 * Created by nikhil on 17/4/16.
 */
public class HotelHelper {
    private String hotelName;
    private int rate;

    public HotelHelper(String hn, int r){
        this.setHotelName(hn);
        this.setRate(r);
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }


}
