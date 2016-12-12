package com.epicodus.pettracker.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.pettracker.Constants;
import com.epicodus.pettracker.R;
import com.epicodus.pettracker.models.Pet;
import com.epicodus.pettracker.models.Weight;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeightActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.todayDate) TextView mTodayDate;
    @Bind(R.id.weight) EditText mWeight;
    @Bind(R.id.button) Button mWeightButton;

    private Pet mPet;
    private GraphView graphView;
    private LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        ButterKnife.bind(this);

        final ArrayList<Weight> weights = new ArrayList<>();
        mPet = Parcels.unwrap(getIntent().getParcelableExtra("pet"));

        Typeface rampung = Typeface.createFromAsset(getAssets(), "fonts/theboldfont.ttf");
        mTodayDate.setTypeface(rampung);
        mWeight.setTypeface(rampung);
        mWeightButton.setTypeface(rampung);

        graphView = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();

        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_WEIGHTS)
                .child(mPet.getPushId());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                  weights.add(snapshot.getValue(Weight.class));
                }
                for(Weight weight : weights){
                    long unixTime = weight.getWeightDate().getTime();
                    float petWeight = weight.getWeight();

                    series.appendData(new DataPoint(unixTime, petWeight), false, 100);
                }

                graphView.addSeries(series);
                graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(WeightActivity.this));
                long today = System.currentTimeMillis();
                graphView.getGridLabelRenderer().setNumHorizontalLabels(2);
//                graphView.getViewport().setMinX(weights.get(0).getWeightDate().getTime());
//                graphView.getViewport().setMaxX(today);
                graphView.getViewport().setXAxisBoundsManual(true);
                graphView.getGridLabelRenderer().setHumanRounding(false);

                series.setDrawDataPoints(true);
                series.setDataPointsRadius(10);
                series.setThickness(8);
                series.setColor(Color.WHITE);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Date today = new Date();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        mTodayDate.setText("Today: " + df.format(today));
        mWeightButton.setOnClickListener(this);

    }
    @Override
    public void onClick(View v){
        if (v == mWeightButton) {
            String weight = mWeight.getText().toString();

            if (weight.equals("")) {
                mWeight.setError("Please enter a weight");
            }
            float weightInt = Float.parseFloat(weight);
            Date todayDate = new Date();
            String petId = mPet.getPushId();
            Weight newWeight = new Weight(todayDate, weightInt, petId);

            DatabaseReference weightRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_WEIGHTS)
                    .child(petId);

            DatabaseReference pushRef = weightRef.push();
            String pushId = pushRef.getKey();
            newWeight.setPushId(pushId);
            pushRef.setValue(newWeight);

            Toast.makeText(WeightActivity.this, "Saved", Toast.LENGTH_SHORT).show();

            startActivity(getIntent());
        }
    }

}
