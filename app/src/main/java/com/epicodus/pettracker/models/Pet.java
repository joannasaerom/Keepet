package com.epicodus.pettracker.models;

import java.io.Serializable;

/**
 * Created by joannaanderson on 11/18/16.
 */

public class Pet implements Serializable {
    private String name;
    private String birthdate;
    private String gender;

    //Constructor
    public Pet(String _name, String _date, String _gender){
        name = _name;
        birthdate = _date;
        gender = _gender;
    }

    public String getName() {
        return name;
    }

    public String getBirthdate(){
        return birthdate;
    }

    public String getGender(){
        return gender;
    }
    public void setName(String _name){
        this.name = _name;
    }
    public void setBirthdate(String _birthdate){
        this.birthdate = _birthdate;
    }
    public void setGender(String _gender){
        this.gender = _gender;
    }
    public String toString(){
        return name;
    }
}
