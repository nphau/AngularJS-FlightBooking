package com.yosta.flightbooking.service;

import com.google.gson.JsonObject;
import com.yosta.flightbooking.model.Airport;
import com.yosta.flightbooking.model.Airports;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public interface IFlightBookingAPI {
    String BASE_URL = "http://flightbooking.mycloud.by/";
    String API_GET_FLIGHTS = "api/flights";
    String API_GET_DEPART_AIRPORTS = "api/flights/depart_airports";
    String API_GET_ARRIVE_AIRPORTS = "api/flights/arrive_airports";

    @GET(API_GET_DEPART_AIRPORTS)
    @Headers("Content-Type: application/json")
    Call<Airports> apiGetDepartAirport();

    @GET(API_GET_ARRIVE_AIRPORTS)
    @Headers("Content-Type: application/json")
    Call<Airports> apiGetArriveAirport(@Body JsonObject jsonObject);

}
