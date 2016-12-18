package com.epicodus.pettracker.ui;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.pettracker.Constants;
import com.epicodus.pettracker.R;
import com.epicodus.pettracker.models.Pet;
import com.epicodus.pettracker.models.Vet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class VetDetailFragment extends Fragment implements View.OnClickListener{
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;
    @Bind(R.id.vetImageView) ImageView mImageLabel;
    @Bind(R.id.vetNameTextView) TextView mNameLabel;
    @Bind(R.id.ratingTextView) TextView mRatingLabel;
    @Bind(R.id.websiteTextView) TextView mWebsiteLabel;
    @Bind(R.id.phoneTextView) TextView mPhoneLabel;
    @Bind(R.id.addressTextView) TextView mAddressLabel;
    @Bind(R.id.saveVet) Button mSaveVetButton;

    private SharedPreferences mSharedPreferences;
    private String mPetPushId;
    private Pet currentPet;
    private Vet mVet;
    private ArrayList<Vet> mVets;
    private int mPosition;

    public static VetDetailFragment newInstance(ArrayList<Vet> vets, Integer position) {
        VetDetailFragment vetDetailFragment = new VetDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_KEY_VETS, Parcels.wrap(vets));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);
        vetDetailFragment.setArguments(args);
        return vetDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mVets = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_VETS));
        mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
        mVet = mVets.get(mPosition);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mPetPushId = mSharedPreferences.getString(Constants.PREFERENCES_PETPUSHID_KEY, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vet_detail, container, false);
        ButterKnife.bind(this, view);

        Typeface rampung = Typeface.createFromAsset(getActivity().getAssets(), "fonts/theboldfont.ttf");
        mNameLabel.setTypeface(rampung);
        mRatingLabel.setTypeface(rampung);
        mWebsiteLabel.setTypeface(rampung);
        mPhoneLabel.setTypeface(rampung);
        mAddressLabel.setTypeface(rampung);
        mSaveVetButton.setTypeface(rampung);

        Picasso.with(view.getContext())
                .load(mVet.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mImageLabel);

        mWebsiteLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);
        mSaveVetButton.setOnClickListener(this);

        mNameLabel.setText(mVet.getName());
        mRatingLabel.setText(Double.toString(mVet.getRating()) + "/5");
        mPhoneLabel.setText(mVet.getPhone());
        mAddressLabel.setText(android.text.TextUtils.join(", ", mVet.getAddress()));

        return view;
    }

    @Override
    public void onClick(View v){
        if (v == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mVet.getWebsite()));
            startActivity(webIntent);
        }
        if (v == mPhoneLabel){
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + mVet.getPhone()));
            startActivity(phoneIntent);
        }
        if (v == mAddressLabel){
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + mVet.getLatitude() + "," + mVet.getLongitude() + "?q=(" + mVet.getName() + ")"));
            startActivity(mapIntent);
        }
        if (v == mSaveVetButton) {
            final ArrayList<Pet> pets = new ArrayList<>();

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final String uid = user.getUid();
            final DatabaseReference petRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_PETS)
                    .child(uid);
            petRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        pets.add(snapshot.getValue(Pet.class));
                    }
                    for (Pet pet : pets) {
                        if(pet.getPushId().equals(mPetPushId)){
                            currentPet = pet;
                        }
                    }
                    currentPet.setVet(mVet);
                    DatabaseReference updateRef = FirebaseDatabase
                            .getInstance()
                            .getReference(Constants.FIREBASE_CHILD_PETS)
                            .child(uid)
                            .child(mPetPushId);
                    updateRef.setValue(currentPet);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }

}
