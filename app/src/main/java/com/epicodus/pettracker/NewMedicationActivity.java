package com.epicodus.pettracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;


public class NewMedicationActivity extends AppCompatActivity {
    @Bind(R.id.medicationName) EditText mMedicationName;
    @Bind(R.id.medicationDetails) EditText mMedicationDetail;
    @Bind(R.id.reminder) EditText mReminder;
    @Bind(R.id.addMedicine) Button mAddMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medication);

        ButterKnife.bind(this);

        mAddMedicine.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = mMedicationName.getText().toString();
                String detail = mMedicationDetail.getText().toString();
                String reminder = mReminder.getText().toString();

                Medication newMedication = new Medication(name, detail, reminder);

                Intent intent = new Intent(NewMedicationActivity.this, MedicationActivity.class);
                intent.putExtra("newMedication", newMedication);
                startActivity(intent);
            }
        });
    }
}
