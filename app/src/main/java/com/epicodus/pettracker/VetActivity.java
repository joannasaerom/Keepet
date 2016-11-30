package com.epicodus.pettracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class VetActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.zipCode) EditText mZipcode;
    @Bind(R.id.searchButton) Button mSearchButton;

    public static final String TAG = VetActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet);

        ButterKnife.bind(this);

        mSearchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v == mSearchButton){
            String location = mZipcode.getText().toString();
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
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, jsonData);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
