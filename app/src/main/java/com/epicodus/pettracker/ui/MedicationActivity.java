package com.epicodus.pettracker.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.epicodus.pettracker.Constants;
import com.epicodus.pettracker.R;
import com.epicodus.pettracker.adapters.FirebaseMedicationViewHolder;
import com.epicodus.pettracker.models.Medication;
import com.epicodus.pettracker.models.Pet;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MedicationActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.recyclerView) RecyclerView mMedicationList;
    @Bind(R.id.newMedication) ImageView mNewMedication;
    @Bind(R.id.pageTitle) TextView mPageTitle;


    private DatabaseReference mMedicationReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    private Pet mPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);

        ButterKnife.bind(this);

        //custom font
        Typeface rampung = Typeface.createFromAsset(getAssets(), "fonts/theboldfont.ttf");
        mPageTitle.setTypeface(rampung);

        //retrieve pet object from the main activity
        mPet = Parcels.unwrap(getIntent().getParcelableExtra("pet"));

        //create database reference to retrieve all medications associated with this pet
        mMedicationReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_MEDICATIONS)
                .child(mPet.getPushId());

        setUpFirebaseAdapter();

        mPageTitle.setText(mPet.getName() +"'s Medications");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //stops adapter from listening for changes in Firebase when we move away from activity
        mFirebaseAdapter.cleanup();
    }

    //creates new adapter for Firebase using the database reference created in onCreate
    private void setUpFirebaseAdapter(){
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Medication, FirebaseMedicationViewHolder>(Medication.class, R.layout.medication_list_item, FirebaseMedicationViewHolder.class, mMedicationReference) {

            //bind data to view
            @Override
            protected void populateViewHolder(FirebaseMedicationViewHolder viewHolder, Medication model, int position) {
                viewHolder.bindMedication(model);
            }
        };

        //settings for medication list recycler view
        mMedicationList.setHasFixedSize(true);
        mMedicationList.setLayoutManager(new LinearLayoutManager(this));
        mMedicationList.setAdapter(mFirebaseAdapter);

    }
}
