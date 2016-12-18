package com.epicodus.pettracker.utils;

import com.epicodus.pettracker.models.Vet;

import java.util.ArrayList;

/**
 * Created by joannaanderson on 12/17/16.
 */

public interface OnVetSelectedListener {
    public void onVetSelected(Integer position, ArrayList<Vet> vets);
}
