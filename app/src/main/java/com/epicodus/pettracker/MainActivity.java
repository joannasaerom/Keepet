package com.epicodus.pettracker;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.weightButton) Button mWeightButton;
    @Bind(R.id.medicationButton) Button mMedicationButton;
    @Bind(R.id.addPet) ImageView mAddPet;
    @Bind(R.id.petName) TextView mPetName;
    @Bind(R.id.birthDate) TextView mBirthDate;
    @Bind(R.id.vetButton) Button mVetButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);

        Typeface rampung = Typeface.createFromAsset(getAssets(), "fonts/Rampung.ttf");
        mPetName.setTypeface(rampung);
        mBirthDate.setTypeface(rampung);
        mWeightButton.setTypeface(rampung);
        mMedicationButton.setTypeface(rampung);
        mVetButton.setTypeface(rampung);

        mWeightButton.setOnClickListener(this);
        mMedicationButton.setOnClickListener(this);
        mAddPet.setClickable(true);
        mAddPet.setOnClickListener(this);
        mVetButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v == mWeightButton) {
            Intent intent = new Intent(MainActivity.this, WeightActivity.class);
            startActivity(intent);
        } else if(v == mMedicationButton){
            Intent intent = new Intent(MainActivity.this, MedicationActivity.class);
            startActivity(intent);
        } else if (v == mAddPet){
            Intent intent = new Intent(MainActivity.this, PetListActivity.class);
            startActivity(intent);
        } else if (v == mVetButton){
            Intent intent = new Intent(MainActivity.this, VetActivity.class);
            startActivity(intent);
        }
    }
}
