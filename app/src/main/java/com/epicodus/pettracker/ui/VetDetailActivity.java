package com.epicodus.pettracker.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.epicodus.pettracker.R;
import com.epicodus.pettracker.Vet;
import com.epicodus.pettracker.adapters.VetPagerAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VetDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private VetPagerAdapter adapterViewPager;
    ArrayList<Vet> mVets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_detail);
        ButterKnife.bind(this);

        mVets = Parcels.unwrap(getIntent().getParcelableExtra("vets"));
        int startingPosition = getIntent().getIntExtra("position", 0);
        adapterViewPager = new VetPagerAdapter(getSupportFragmentManager(), mVets);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
