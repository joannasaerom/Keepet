package com.epicodus.pettracker;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

/**
 * Created by joannaanderson on 11/30/16.
 */

public class YelpService {

    public static final String TAG = YelpService.class.getSimpleName();

    public static void findVets(String location, Callback callback){
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(Constants.YELP_CONSUMER_KEY, Constants.YELP_CONSUMER_SECRET);
        consumer.setTokenWithSecret(Constants.YELP_TOKEN, Constants.YELP_TOKEN_SECRET);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YELP_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.YELP_LOCATION_QUERY_PARAMETER, location);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Vet> processResults(Response response){
        ArrayList<Vet> vets = new ArrayList<>();

        try{
            String jsonData = response.body().string();
            Log.d(TAG, response.body().string());
            if(response.isSuccessful()){
                Log.d(TAG, "Made it");
                JSONObject yelpJSON = new JSONObject(jsonData);
                JSONArray businessesJSON = yelpJSON.getJSONArray("businesses");
                for (int i = 0; i < businessesJSON.length(); i++){
                    JSONObject vetJSON = businessesJSON.getJSONObject(i);
                    String name = vetJSON.getString("name");
                    String phone = vetJSON.optString("display_phone", "Phone not available");
                    String website = vetJSON.getString("url");
                    double rating = vetJSON.getDouble("rating");
                    String imageUrl = vetJSON.getString("image_url");
                    double latitude = vetJSON.getJSONObject("location")
                            .getJSONObject("coordinate").getDouble("latitude");
                    double longitude = vetJSON.getJSONObject("location")
                            .getJSONObject("coordinate").getDouble("longitude");
                    ArrayList<String> address = new ArrayList<>();
                    JSONArray addressJSON = vetJSON.getJSONObject("location")
                            .getJSONArray("display_address");
                    for (int y = 0; y < addressJSON.length(); y++){
                        address.add(addressJSON.get(y).toString());
                    }
                    Vet vet = new Vet(name, phone, website, rating, imageUrl, address, latitude, longitude);
                    vets.add(vet);

                }
            } else{
                Log.d(TAG, response.message());
                Log.d(TAG, "fail");
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return vets;
    }
}
