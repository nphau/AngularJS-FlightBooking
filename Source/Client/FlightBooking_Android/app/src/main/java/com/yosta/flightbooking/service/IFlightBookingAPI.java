package com.yosta.flightbooking.service;

import com.yosta.flightbooking.model.Airports;
import com.yosta.flightbooking.model.Booking;
import com.yosta.flightbooking.model.Flights;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public interface IFlightBookingAPI {
    String BASE_URL = "http://flightbooking.mycloud.by/";
    String API_GET_FLIGHTS = "api/flights";
    String API_GET_DEPART_AIRPORTS = "api/flights/depart_airports";
    String API_GET_ARRIVE_AIRPORTS = "api/flights/arrive_airports";
    String API_BOOKING = "/api/booking";

    @GET(API_GET_DEPART_AIRPORTS)
    @Headers("Content-Type: application/json")
    Call<Airports> apiGetDepartAirport();

    @GET(API_GET_ARRIVE_AIRPORTS + "/{depart}")
    Call<Airports> apiGetArriveAirport(@Path("depart") String departId);

    @GET(API_BOOKING + "/{bookingId}")
    Call<Airports> apiGetBooking(@Path("bookingId") String bookingId);

    @GET("api/flight_detail/{bookingId}/flights")
    Call<Airports> apiGetFlightDetail(@Path("bookingId") String bookingId);

    @GET(API_GET_FLIGHTS)
    Call<Flights> apiFind(@QueryMap(encoded = true) Map<String, String> params);

    @POST(API_BOOKING)
    @Headers("Content-Type: application/json")
    Call<Booking> apiBooking();

/*
    @PUT(API_BOOKING + "/{bookingId}")
    Call<Cost> apiUpdateBooking(@Path("bookingId") String bookingId);*/
}
