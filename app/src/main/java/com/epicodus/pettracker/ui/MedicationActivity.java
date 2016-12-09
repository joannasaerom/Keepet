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

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MedicationActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.medicationList) ListView mMedicationList;
    @Bind(R.id.newMedication) ImageView mNewMedication;
    @Bind(R.id.pageTitle) TextView mPageTitle;
    ArrayList<Medication> medications = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);

        Medication heartguard = new Medication("HeartGuard", "Taken for heart worms", "1st Monday of each month");
        medications.add(heartguard);

        ButterKnife.bind(this);

        Typeface rampung = Typeface.createFromAsset(getAssets(), "fonts/Rampung.ttf");
        mPageTitle.setTypeface(rampung);


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, medications);
        adapter.notifyDataSetChanged();
        mMedicationList.setAdapter(adapter);

        mNewMedication.setOnClickListener(this);

        Intent intent = getIntent();
        Medication newMedication = (Medication) intent.getSerializableExtra("newMedication");
        if (newMedication !=null){
            medications.add(newMedication);
        }


    }

    @Override
    public void onClick(View v){
        if(v == mNewMedication) {
            Intent intent = new Intent(MedicationActivity.this, NewMedicationActivity.class);
            startActivity(intent);
        }
    }
}
