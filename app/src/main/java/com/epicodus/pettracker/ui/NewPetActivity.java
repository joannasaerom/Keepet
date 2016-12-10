package com.epicodus.pettracker.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.epicodus.pettracker.Constants;
import com.epicodus.pettracker.R;
import com.epicodus.pettracker.models.Pet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewPetActivity extends AppCompatActivity {
    @Bind(R.id.petName) EditText mPetName;
    @Bind(R.id.birthDate) EditText mBirthdate;
    @Bind(R.id.gender) EditText mGender;
    @Bind(R.id.addPet) Button mAddPet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pet);

        ButterKnife.bind(this);

        mAddPet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = mPetName.getText().toString();
                String birthDateText = mBirthdate.getText().toString();
                DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
                Date birthDate = new Date();
                try {
                    birthDate = df.parse(birthDateText);
                } catch (ParseException e){
                    e.printStackTrace();
                }
                String gender = mGender.getText().toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();

                Pet newPet = new Pet(name, birthDate, gender, uid);

                DatabaseReference petRef = FirebaseDatabase
                        .getInstance()
                        .getReference(Constants.FIREBASE_CHILD_PETS)
                        .child(uid);

                DatabaseReference pushRef = petRef.push();
                String pushId = pushRef.getKey();
                newPet.setPushId(pushId);
                pushRef.setValue(newPet);

                Toast.makeText(NewPetActivity.this, "Saved", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(NewPetActivity.this, PetListActivity.class);
                startActivity(intent);
            }
        });




    }

}
