package com.epicodus.pettracker.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.epicodus.pettracker.R;
import com.epicodus.pettracker.models.Medication;
import com.epicodus.pettracker.models.Pet;


import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MedicationActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.newMedication) ImageView mNewMedication;
    @Bind(R.id.pageTitle) TextView mPageTitle;

    private Pet mPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);

        ButterKnife.bind(this);

        Typeface rampung = Typeface.createFromAsset(getAssets(), "fonts/Rampung.ttf");
        mPageTitle.setTypeface(rampung);

        mPet = Parcels.unwrap(getIntent().getParcelableExtra("pet"));


        mNewMedication.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v == mNewMedication) {
            Intent intent = new Intent(MedicationActivity.this, NewMedicationActivity.class);
            intent.putExtra("pet", Parcels.wrap(mPet));
            startActivity(intent);
        }
    }
}
