package com.epicodus.pettracker.models;

import org.parceler.Parcel;

import java.io.Serializable;

/**
 * Created by joannaanderson on 11/18/16.
 */

@Parcel
public class Medication{
    String name;
    String detail;
    String reminder;
    String pushId;
    String petId;

    public Medication(){}

    public Medication(String _name, String _detail, String _reminder, String _petId){
        name = _name;
        detail = _detail;
        reminder = _reminder;
        petId = _petId;
    }

    public String getName() {
        return name;
    }

    public String getDetail(){
        return detail;
    }

    public String getReminder(){
        return reminder;
    }

    public void setName(String _name){
        name = _name;
    }

    public void setDetail(String _detail){
        detail = _detail;
    }

    public void setReminder(String _reminder){
        reminder = _reminder;
    }

    public String toString() {
        return name;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }
}
