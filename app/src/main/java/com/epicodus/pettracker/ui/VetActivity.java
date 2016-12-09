package com.epicodus.pettracker.ui;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.epicodus.pettracker.Constants;
import com.epicodus.pettracker.R;
import com.epicodus.pettracker.adapters.VetListAdapter;
import com.epicodus.pettracker.models.Vet;
import com.epicodus.pettracker.services.YelpService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class VetActivity extends AppCompatActivity implements View.OnClickListener{


    @Bind(R.id.zipCode) EditText mZipcode;
    @Bind(R.id.searchButton) Button mSearchButton;
    @Bind(R.id.vetList) RecyclerView mVetList;

    public ArrayList<Vet> mVets = new ArrayList<>();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private VetListAdapter mAdapter;
    private String mRecentAddress;

    public static final String TAG = VetActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet);

        ButterKnife.bind(this);

        Typeface rampung = Typeface.createFromAsset(getAssets(), "fonts/Rampung.ttf");
        mZipcode.setTypeface(rampung);
        mSearchButton.setTypeface(rampung);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);

        if (mRecentAddress != null){
            getVets(mRecentAddress);
        }

        mSearchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v == mSearchButton){
            String location = mZipcode.getText().toString();
            if(!(location).equals("")){
                addToSharedPreferences(location);
            }

            getVets(location);
        }
    }

    private void getVets(String location){

        final YelpService yelpService = new YelpService();
        yelpService.findVets(location, new Callback(){

            @Override
            public void onFailure(Call call, IOException e){
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {

                mVets = yelpService.processResults(response);


                VetActivity.this.runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        mAdapter = new VetListAdapter(getApplicationContext(), mVets);
                        mVetList.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(VetActivity.this);
                        mVetList.setLayoutManager(layoutManager);
                        mVetList.setHasFixedSize(true);

                    }
                });

            }
        });
    }

    private void addToSharedPreferences(String location){
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    }
}
