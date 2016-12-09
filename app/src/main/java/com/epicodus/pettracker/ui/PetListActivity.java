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
import com.epicodus.pettracker.models.Pet;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PetListActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.petList) ListView mPetList;
    @Bind(R.id.addPet) ImageView mAddPetButton;
    @Bind(R.id.pageTitle) TextView mPageTitle;
    ArrayList<Pet> pets = new ArrayList<Pet>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);


        ButterKnife.bind(this);

        Typeface rampung = Typeface.createFromAsset(getAssets(), "fonts/Rampung.ttf");
        mPageTitle.setTypeface(rampung);

        Pet juniper = new Pet("Juniper", "December 24", "Female");
        pets.add(juniper);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pets);
        adapter.notifyDataSetChanged();
        mPetList.setAdapter(adapter);

        Intent intent = getIntent();
        Pet newPet = (Pet) intent.getSerializableExtra("newPet");
        if (newPet !=null){
            pets.add(newPet);
        }

        mAddPetButton.setOnClickListener(this);
//        mPetList.setOnClickListener(this);



    }
    @Override
    public void onClick(View v){
        if(v == mAddPetButton) {
            Intent intent = new Intent(PetListActivity.this, NewPetActivity.class);
            startActivity(intent);
        } else if(v == mPetList){
            Intent intent = new Intent(PetListActivity.this, MainActivity.class);
            //Figure out what goes here
        }
    }

}
