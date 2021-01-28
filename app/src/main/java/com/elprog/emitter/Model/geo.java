package com.elprog.emitter.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class geo implements Serializable {
    private String lat,lng;

    public geo(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }



    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }


}
