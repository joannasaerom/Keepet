package com.epicodus.pettracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.epicodus.pettracker.Constants;
import com.epicodus.pettracker.R;
import com.epicodus.pettracker.models.Pet;
import com.epicodus.pettracker.ui.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by joannaanderson on 12/9/16.
 */

public class FirebasePetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    View mView;
    Context mContext;
    private Pet mPet;

    public FirebasePetViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindPet(Pet pet){
        TextView petNameTextView = (TextView) mView.findViewById(R.id.petName);

        petNameTextView.setText(pet.getName());
    }

    @Override
    public void onClick(View v){
        final ArrayList<Pet> pets = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_PETS)
                .child(uid);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    pets.add(snapshot.getValue(Pet.class));
                }
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, MainActivity.class);
                    mPet = pets.get(itemPosition);
                intent.putExtra("pet", Parcels.wrap(mPet));
//                intent.putExtra("position", itemPosition + "");
//                intent.putExtra("pet", Parcels.wrap(pets));
                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
