package com.epicodus.pettracker;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewPetActivity extends AppCompatActivity {
    @Bind(R.id.petName) EditText mPetName;
    @Bind(R.id.birthDate) EditText mBirthdate;
//    @Bind(R.id.genderSpinner) Spinner mGender;
    @Bind(R.id.gender) EditText mGender;
    @Bind(R.id.addPet) Button mAddPet;

    public static final String TAG = NewPetActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pet);

        ButterKnife.bind(this);

        mAddPet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = mPetName.getText().toString();
                String birthdate = mBirthdate.getText().toString();
                String gender = mGender.getText().toString();

                Pet newPet = new Pet(name, birthdate, gender);

//                mGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        gender =  mGender.getSelectedItem().toString();
//                    }
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });

                Intent intent = new Intent(NewPetActivity.this, PetListActivity.class);
                intent.putExtra("newPet", newPet);
                startActivity(intent);
            }
        });




    }

}
