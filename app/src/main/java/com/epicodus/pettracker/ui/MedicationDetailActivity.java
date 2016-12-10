package com.epicodus.pettracker.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.epicodus.pettracker.R;
import com.epicodus.pettracker.models.Medication;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MedicationDetailActivity extends AppCompatActivity {

    @Bind(R.id.medicationName) TextView medicationNameTextView;
    @Bind(R.id.medicationDetails) TextView medicationDetailTextView;

    private Medication mMedication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_detail);
        ButterKnife.bind(this);

        mMedication = Parcels.unwrap(getIntent().getParcelableExtra("medication"));

        medicationNameTextView.setText(mMedication.getName());
        medicationDetailTextView.setText(mMedication.getDetail());
    }
}
