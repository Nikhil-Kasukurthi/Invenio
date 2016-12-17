package com.gdgvitvellore.smile;

/**
 * Created by nikhil on 9/4/16.
 */
public class NearbyHelper {
    String description;
    String start;
    String distance;
    String duration;
    String end;
    String img;

    public NearbyHelper(String des,String start, String dist, String dur, String end, String img){
        this.setDescription(des);
        this.setDistance(dist);
        this.setDuration(dur);
        this.setStart(start);
        this.setEnd(end);
        this.setImg(img);
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
