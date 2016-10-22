package com.yosta.flightbooking.service;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yosta.flightbooking.model.Airport;
import com.yosta.flightbooking.model.Airports;
import com.yosta.flightbooking.model.Depart;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public class FlightBookingAPI {

    private Gson mGson = null;
    private JsonParser mParser = null;
    private String jsonSamples = null;
    private IFlightBookingAPI iFlightBookingAPI = null;

    private static FlightBookingAPI ourInstance = null;

    private FlightBookingAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IFlightBookingAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.iFlightBookingAPI = retrofit.create(IFlightBookingAPI.class);

        this.mGson = new Gson();
        this.mParser = new JsonParser();
    }

    public static FlightBookingAPI getInstance(Activity activity) {
        if (ourInstance == null) {
            ourInstance = new FlightBookingAPI();
        }
        return ourInstance;
    }

    public void callRestFulAPI(FlightBookingAPIType type, Callback<Airports> callback, String... params) {
        try {

            switch (type) {
                case API_GET_DEPART_AIRPORT: {
                    Call<Airports> getDepartAirport = this.iFlightBookingAPI.apiGetDepartAirport();
                    getDepartAirport.enqueue(callback);
                    break;
                }
                case API_GET_ARRIVE_AIRPORT: {
                    if (params.length > 0) {
                        String airportId = params[0];
                        jsonSamples = mGson.toJson(new Depart(airportId));
                        JsonObject object = mParser.parse(jsonSamples).getAsJsonObject();
                        Call<Airports> getArriveAirport = this.iFlightBookingAPI.apiGetArriveAirport(object);
                        getArriveAirport.enqueue(callback);
                    }
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
