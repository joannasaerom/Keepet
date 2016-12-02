package com.epicodus.pettracker.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.epicodus.pettracker.Vet;
import com.epicodus.pettracker.ui.VetDetailFragment;

import java.util.ArrayList;

/**
 * Created by joannaanderson on 12/2/16.
 */

public class VetPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Vet> mVets;

    public VetPagerAdapter(FragmentManager fm, ArrayList<Vet> vets){
        super(fm);
        mVets = vets;
    }

    @Override
    public Fragment getItem(int position){
        return VetDetailFragment.newInstance(mVets.get(position));
    }
    @Override
    public int getCount(){
        return mVets.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mVets.get(position).getName();
    }
}
