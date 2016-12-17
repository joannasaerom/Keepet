package com.epicodus.pettracker.models;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by joannaanderson on 11/18/16.
 */

@Parcel
public class Pet {
    String name;
    Date birthdate;
    String gender;
    String userId;
    String pushId;
    String imageUrl;
    Vet vet;

    public Pet(){}

    //Constructor
    public Pet(String _name, Date _date, String _gender, String _userId){
        name = _name;
        birthdate = _date;
        gender = _gender;
        userId = _userId;
    }

    public String getName() {
        return name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getGender() {
        return gender;
    }

    public String getUserId() {
        return userId;
    }

    public String getPushId() {
        return pushId;
    }

    public Vet getVet() {
        return vet;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
