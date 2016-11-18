package com.epicodus.pettracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PetListActivity extends AppCompatActivity {
    @Bind(R.id.petList) ListView mPetList;
    ArrayList<Pet> pets = new ArrayList<Pet>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        ButterKnife.bind(this);

        Pet juniper = new Pet("Juniper", "December 24", "Female");
        pets.add(juniper);


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pets);
        mPetList.setAdapter(adapter);

        Intent intent = getIntent();
        if (intent !=null){
            Pet newPet = (Pet) intent.getSerializableExtra("newPet");
        }
    }
}
