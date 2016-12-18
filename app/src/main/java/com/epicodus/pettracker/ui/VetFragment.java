package com.epicodus.pettracker.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epicodus.pettracker.Constants;
import com.epicodus.pettracker.R;
import com.epicodus.pettracker.adapters.VetListAdapter;
import com.epicodus.pettracker.models.Vet;
import com.epicodus.pettracker.services.YelpService;
import com.epicodus.pettracker.utils.OnVetSelectedListener;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class VetFragment extends Fragment {
    @Bind(R.id.vetList) RecyclerView mVetList;
//    @Bind(R.id.textView) TextView mText;

    public ArrayList<Vet> mVets = new ArrayList<>();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private VetListAdapter mAdapter;
    private String mRecentAddress;
    private OnVetSelectedListener mOnVetSelectedListener;

    public VetFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            mOnVetSelectedListener = (OnVetSelectedListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vet, container, false);

        ButterKnife.bind(this, view);

//        Typeface rampung = Typeface.createFromAsset(getActivity().getAssets(), "fonts/theboldfont.ttf");
//        mText.setTypeface(rampung);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);

        if (mRecentAddress != null){
            getVets(mRecentAddress);
        }

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query){
                addToSharedPreferences(query);
                getVets(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText){
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }

    private void getVets(String location){
//        mText.setVisibility(View.GONE);

        final YelpService yelpService = new YelpService();
        yelpService.findVets(location, new Callback(){

            @Override
            public void onFailure(Call call, IOException e){
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {

                mVets = yelpService.processResults(response);


                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        mAdapter = new VetListAdapter(getActivity().getApplicationContext(), mVets, mOnVetSelectedListener);
                        mVetList.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(getActivity());
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
