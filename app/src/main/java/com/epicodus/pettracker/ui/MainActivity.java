package com.epicodus.pettracker.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.pettracker.Constants;
import com.epicodus.pettracker.R;
import com.epicodus.pettracker.models.Pet;
import com.epicodus.pettracker.models.Vet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.weightButton) Button mWeightButton;
    @Bind(R.id.medicationButton) Button mMedicationButton;
    @Bind(R.id.addPet) ImageView mAddPet;
    @Bind(R.id.petName) TextView mPetName;
    @Bind(R.id.birthDate) TextView mBirthDate;
    @Bind(R.id.vetButton) Button mVetButton;
    @Bind(R.id.circularImageView) CircularImageView mCircularImageView;

    private Pet mPet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mPet = Parcels.unwrap(getIntent().getParcelableExtra("pet"));

        if (mPet != null){
            DateFormat df = new SimpleDateFormat("MMM dd");
            Date birthDate = mPet.getBirthdate();
            String stringDate = df.format(birthDate);
            String genderShortened;

            if (mPet.getGender().equals("Female")){
                genderShortened = "F";
            } else {
                genderShortened = "M";
            }

            String imageUrl = mPet.getImageUrl();

            if (imageUrl != null) {
                try {
                    Bitmap imageBitmap = decodeFromFirebase64(mPet.getImageUrl());
                    mCircularImageView.setImageBitmap(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            mPetName.setText(mPet.getName() + " / " + genderShortened);
            mBirthDate.setText("Birthdate: " + stringDate);
        }


        Typeface rampung = Typeface.createFromAsset(getAssets(), "fonts/theboldfont.ttf");
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
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_logout){
            logout();
            return true;
        }
        if (id == R.id.action_search_vet){
            Intent i = new Intent(this, VetActivity.class);
            i.putExtra("pet", Parcels.wrap("mPet"));
            this.startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){
        if(v == mWeightButton) {
            Intent intent = new Intent(MainActivity.this, WeightActivity.class);
            intent.putExtra("pet", Parcels.wrap(mPet));
            startActivity(intent);
        } else if(v == mMedicationButton){
            Intent intent = new Intent(MainActivity.this, MedicationActivity.class);
            intent.putExtra("pet", Parcels.wrap(mPet));
            Log.d("MainActivity", mPet.getName());
            startActivity(intent);
        } else if (v == mAddPet){
            Intent intent = new Intent(MainActivity.this, PetListActivity.class);
            startActivity(intent);
        } else if (v == mVetButton){
            Vet vet = mPet.getVet();
            if (vet == null){
                Intent intent = new Intent(MainActivity.this, VetActivity.class);
                startActivity(intent);
            } else{
                Intent intent = new Intent(MainActivity.this, PetsVetDetailActivity.class);
                intent.putExtra("pet", Parcels.wrap(mPet));
                startActivity(intent);
            }

        }
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public static Bitmap decodeFromFirebase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

}
