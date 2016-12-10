package com.epicodus.pettracker.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.pettracker.Constants;
import com.epicodus.pettracker.R;
import com.epicodus.pettracker.adapters.FirebasePetViewHolder;
import com.epicodus.pettracker.models.Pet;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PetListActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.addPet) ImageView mAddPetButton;
    @Bind(R.id.pageTitle) TextView mPageTitle;
    @Bind(R.id.recyclerView) RecyclerView mPetList;

    private DatabaseReference mPetReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);


        ButterKnife.bind(this);

        Typeface rampung = Typeface.createFromAsset(getAssets(), "fonts/Rampung.ttf");
        mPageTitle.setTypeface(rampung);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mPetReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_PETS)
                .child(uid);
        setUpFirebaseAdapter();
        mAddPetButton.setOnClickListener(this);

    }
    @Override
    public void onClick(View v){
        if(v == mAddPetButton) {
            Intent intent = new Intent(PetListActivity.this, NewPetActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Pet, FirebasePetViewHolder>(Pet.class, R.layout.pet_list_item, FirebasePetViewHolder.class, mPetReference) {
            @Override
            protected void populateViewHolder(FirebasePetViewHolder viewHolder, Pet model, int position) {
            viewHolder.bindPet(model);
            }
        };
        mPetList.setHasFixedSize(true);
        mPetList.setLayoutManager(new LinearLayoutManager(this));
        mPetList.setAdapter(mFirebaseAdapter);
    }
}
