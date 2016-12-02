package com.epicodus.pettracker;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeightActivity extends AppCompatActivity {
    @Bind(R.id.todayDate) EditText mTodayDate;
    @Bind(R.id.weight) EditText mWeight;
    @Bind(R.id.button) Button mWeightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        ButterKnife.bind(this);

        Typeface rampung = Typeface.createFromAsset(getAssets(), "fonts/Rampung.ttf");
        mTodayDate.setTypeface(rampung);
        mWeight.setTypeface(rampung);
        mWeightButton.setTypeface(rampung);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
    }
}
