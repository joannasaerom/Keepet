package com.epicodus.pettracker.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.epicodus.pettracker.Constants;
import com.epicodus.pettracker.R;
import com.epicodus.pettracker.models.Medication;
import com.epicodus.pettracker.models.Pet;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;


public class NewMedicationActivity extends AppCompatActivity {
    @Bind(R.id.medicationName) EditText mMedicationName;
    @Bind(R.id.medicationDetails) EditText mMedicationDetail;
    @Bind(R.id.reminder) EditText mReminder;
    @Bind(R.id.addMedicine) Button mAddMedicine;

    private Pet mPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medication);

        ButterKnife.bind(this);


        mPet = Parcels.unwrap(getIntent().getParcelableExtra("pet"));
        Log.d("NewMed", mPet.getName());

        mAddMedicine.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = mMedicationName.getText().toString();
                String detail = mMedicationDetail.getText().toString();
                String reminder = mReminder.getText().toString();
                if (name.equals("")){
                    mMedicationName.setError("Please enter medication name");
                    return;
                }
                if (detail.equals("")){
                    mMedicationDetail.setError("Please enter details regarding the medication");
                    return;
                }

                String petId = mPet.getPushId();

                Medication medication = new Medication(name, detail, reminder, petId);

                DatabaseReference medicationRef = FirebaseDatabase
                        .getInstance()
                        .getReference(Constants.FIREBASE_CHILD_MEDICATIONS)
                        .child(petId);

                DatabaseReference pushRef = medicationRef.push();
                String pushId = pushRef.getKey();
                medication.setPushId(pushId);
                pushRef.setValue(medication);

                Toast.makeText(NewMedicationActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(NewMedicationActivity.this, MedicationActivity.class);
                intent.putExtra("pet", Parcels.wrap(mPet));
                startActivity(intent);
            }
        });
    }
}
