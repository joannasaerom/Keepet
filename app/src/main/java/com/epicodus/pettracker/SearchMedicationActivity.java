package com.epicodus.pettracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchMedicationActivity extends AppCompatActivity {
    private String[] medications = new String[] {"Nexxguard", "HeartGuard", "Test Medication1", "Test Medication2"};
    @Bind(R.id.searchMedicationList) ListView mSearchMedicationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_medication);

        ButterKnife.bind(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, medications);
        mSearchMedicationList.setAdapter(adapter);
    }
}
