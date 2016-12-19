package com.epicodus.pettracker.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.epicodus.pettracker.models.Vet;
import com.epicodus.pettracker.ui.VetDetailFragment;

import java.util.ArrayList;

/**
 * Created by joannaanderson on 12/2/16.
 */

public class VetPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Vet> mVets;

    //constructor
    public VetPagerAdapter(FragmentManager fm, ArrayList<Vet> vets){
        super(fm);
        mVets = vets;
    }

    //returns instance of fragment with vet in the position provided in argument
    @Override
    public Fragment getItem(int position){
        return VetDetailFragment.newInstance(mVets, position);
    }

    //counts how many vets are in the array list to determine how many fragments adapter needs to create
    @Override
    public int getCount(){
        return mVets.size();
    }

    //updates title in pager adapter with vet's name
    @Override
    public CharSequence getPageTitle(int position){
        return mVets.get(position).getName();
    }
}
