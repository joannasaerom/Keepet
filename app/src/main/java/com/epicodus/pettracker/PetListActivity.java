package com.epicodus.pettracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.Bind;

public class PetListActivity extends AppCompatActivity {
    @Bind(R.id.petList) ListView mPetList;
    ArrayList<Pet> pets = new ArrayList<Pet>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);
        Intent intent = getIntent();
        Pet newPet = (Pet) intent.getSerializableExtra("newPet");
        pets.add(newPet);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pets);
        mPetList.setAdapter(adapter);
    }
}
