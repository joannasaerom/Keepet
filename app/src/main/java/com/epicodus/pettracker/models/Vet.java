package com.epicodus.pettracker.models;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by joannaanderson on 11/30/16.
 */

@Parcel
public class Vet {
    String mName;
    String mPhone;
    String mWebsite;
    double mRating;
    String mImageUrl;
    ArrayList<String> mAddress = new ArrayList<>();
    double mLatitude;
    double mLongitude;
    String pushId;
    String petId;

    public Vet() {}

    public Vet(String name, String phone, String website, double rating, String imageUrl, ArrayList<String> address, double latitude, double longitude){
        this.mName = name;
        this.mPhone = phone;
        this.mWebsite = website;
        this.mRating = rating;
        this.mImageUrl = getLargeImageUrl(imageUrl);
        this.mAddress = address;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
    }

    public String getName(){
        return mName;
    }
    public String getPhone(){
        return mPhone;
    }
    public String getWebsite(){
        return mWebsite;
    }
    public double getRating(){
        return mRating;
    }
    public String getImageUrl(){
        return mImageUrl;
    }
    public ArrayList<String> getAddress(){
        return mAddress;
    }
    public double getLatitude(){
        return mLatitude;
    }
    public double getLongitude(){
        return mLongitude;
    }
    public String getLargeImageUrl(String imageUrl){
        String largeImageUrl = imageUrl.substring(0, imageUrl.length() -6).concat("o.jpg");
        return largeImageUrl;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
