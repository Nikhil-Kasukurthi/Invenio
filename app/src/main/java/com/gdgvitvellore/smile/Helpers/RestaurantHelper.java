package com.gdgvitvellore.smile.Helpers;

/**
 * Created by nikhil on 17/4/16.
 */
public class RestaurantHelper {

    public RestaurantHelper(String n, int rate){
        this.setName(n);
        this.setRate(rate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    String name;
    int rate;
}
