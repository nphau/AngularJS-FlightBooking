package com.yosta.flightbooking.networking;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yosta.flightbooking.model.booking.Booking;
import com.yosta.flightbooking.model.Success;
import com.yosta.flightbooking.model.flight.FlightBooking;

import java.util.Map;

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

    /*public void callAPIFind(Map<String, String> params, Callback<Flights> callback) {
        try {
            Call<Flights> getDepartAirport = this.iFlightBookingAPI.apiFind(params);
            getDepartAirport.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void callAPIBooking(Callback<Booking> callback) {
        try {
            Call<Booking> bookingCall = this.iFlightBookingAPI.apiBooking();
            bookingCall.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void callAPIBookingFlight(String bookingId, FlightBooking flightBooking, Callback<Success> callback) {
        try {
            jsonSamples = mGson.toJson(flightBooking);
            JsonObject object = mParser.parse(jsonSamples).getAsJsonObject();
            Call<Success> bookingFlightCall = this.iFlightBookingAPI.apiBookingFlight(bookingId, object);
            bookingFlightCall.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
