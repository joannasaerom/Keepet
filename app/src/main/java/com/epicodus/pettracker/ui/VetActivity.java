package com.epicodus.pettracker.ui;


import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.epicodus.pettracker.Constants;
import com.epicodus.pettracker.R;
import com.epicodus.pettracker.models.Vet;
import com.epicodus.pettracker.utils.OnVetSelectedListener;

import org.parceler.Parcels;

import java.util.ArrayList;


public class VetActivity extends AppCompatActivity  implements OnVetSelectedListener{
    private Integer mPosition;
    ArrayList<Vet> mVets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet);

        //Checks to see if user was using this activity
        if (savedInstanceState != null){
            //Checks to see if phone is in portrait view
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                mPosition = savedInstanceState.getInt(Constants.EXTRA_KEY_POSITION);
                mVets = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_KEY_VETS));
                //if position and vet is available user will be taken to the vet detail activity
                if (mPosition != null && mVets != null){
                    Intent intent = new Intent(this, VetDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, mPosition);
                    intent.putExtra(Constants.EXTRA_KEY_VETS, Parcels.wrap(mVets));
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        if (mPosition != null && mVets != null){
            outState.putInt(Constants.EXTRA_KEY_POSITION, mPosition);
            outState.putParcelable(Constants.EXTRA_KEY_VETS, Parcels.wrap(mVets));
        }
    }

    @Override
    public void onVetSelected(Integer position, ArrayList<Vet> vets){
        mPosition = position;
        mVets = vets;
    }
}
