package com.epicodus.pettracker.models;

import java.util.Date;

/**
 * Created by joannaanderson on 12/10/16.
 */

public class Weight {
    Date weightDate;
    int weight;
    String petId;
    String pushId;

    public Weight() {}

    public Weight(Date weightDate, int weight, String petId){
        this.weightDate = weightDate;
        this.weight = weight;
        this.petId = petId;
    }

    public int getWeight() {
        return weight;
    }

    public Date getWeightDate() {
        return weightDate;
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
