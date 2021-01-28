package com.elprog.emitter.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.io.Serializable;

public class Address implements Serializable {
    private String street,suite,city,zipcode;
    private geo geo;

    public Address(String street, String suite, String city, String zipcode, geo geo) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.geo = geo;
    }



    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public com.elprog.emitter.Model.geo getGeo() {
        return geo;
    }

    public void setGeo(com.elprog.emitter.Model.geo geo) {
        this.geo = geo;
    }

}