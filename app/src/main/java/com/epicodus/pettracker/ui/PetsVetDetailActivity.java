package com.epicodus.pettracker.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.pettracker.R;
import com.epicodus.pettracker.models.Pet;
import com.epicodus.pettracker.models.Vet;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PetsVetDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;
    @Bind(R.id.vetImageView) ImageView mImageLabel;
    @Bind(R.id.vetNameTextView) TextView mNameLabel;
    @Bind(R.id.websiteTextView) TextView mWebsiteLabel;
    @Bind(R.id.phoneTextView) TextView mPhoneLabel;
    @Bind(R.id.addressTextView) TextView mAddressLabel;

    private Vet mVet;
    private Pet mPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_vet_detail);

        ButterKnife.bind(this);

        mPet = Parcels.unwrap(getIntent().getParcelableExtra("pet"));
        mVet = mPet.getVet();

//
//        Typeface rampung = Typeface.createFromAsset(getAssets(), "fonts/Rampung.ttf");
//        mNameLabel.setTypeface(rampung);
//        mWebsiteLabel.setTypeface(rampung);
//        mPhoneLabel.setTypeface(rampung);
//        mAddressLabel.setTypeface(rampung);

        Picasso.with(PetsVetDetailActivity.this)
                .load(mVet.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mImageLabel);

        mWebsiteLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);

        mNameLabel.setText(mVet.getName());
        mPhoneLabel.setText(mVet.getPhone());
        mAddressLabel.setText(android.text.TextUtils.join(", ", mVet.getAddress()));


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
    }
}
