package com.epicodus.pettracker;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.weightButton) Button mWeightButton;
    @Bind(R.id.medicationButton) Button mMedicationButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);

        mWeightButton.setOnClickListener(this);
        mMedicationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v == mWeightButton) {
            Intent intent = new Intent(MainActivity.this, WeightActivity.class);
            startActivity(intent);
        } else if(v == mMedicationButton){
            Intent intent = new Intent(MainActivity.this, MedicationActivity.class);
            startActivity(intent);
        }
    }
}
