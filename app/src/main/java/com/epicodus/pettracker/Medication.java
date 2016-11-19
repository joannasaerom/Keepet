package com.epicodus.pettracker;

import java.io.Serializable;

/**
 * Created by joannaanderson on 11/18/16.
 */

public class Medication implements Serializable {
    private String name;
    private String detail;
    private String reminder;

    public Medication(String _name, String _detail, String _reminder){
        name = _name;
        detail = _detail;
        reminder = _reminder;
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
}
