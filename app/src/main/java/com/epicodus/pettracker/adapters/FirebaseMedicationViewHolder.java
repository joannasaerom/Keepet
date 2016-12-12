package com.epicodus.pettracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.epicodus.pettracker.Constants;
import com.epicodus.pettracker.R;
import com.epicodus.pettracker.models.Medication;
import com.epicodus.pettracker.ui.MedicationDetailActivity;
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

public class FirebaseMedicationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    View mView;
    Context mContext;
    private Medication mMedication;

    public FirebaseMedicationViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindMedication(Medication medication){
        mMedication = medication;
        TextView medicationNameTextView = (TextView) mView.findViewById(R.id.medicationName);

        medicationNameTextView.setText(medication.getName());
    }

    @Override
    public void onClick(View v){
        final ArrayList<Medication> medications = new ArrayList<>();
        String petId = mMedication.getPetId();
        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_MEDICATIONS)
                .child(petId);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    medications.add(snapshot.getValue(Medication.class));
                }
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, MedicationDetailActivity.class);
                intent.putExtra("medication", Parcels.wrap(mMedication));
                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
