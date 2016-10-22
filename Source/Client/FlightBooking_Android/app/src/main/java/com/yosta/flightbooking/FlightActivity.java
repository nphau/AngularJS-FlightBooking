package com.yosta.flightbooking;

import android.os.Bundle;

import com.yosta.flightbooking.base.ActivityBehavior;
import com.yosta.flightbooking.model.Airports;
import com.yosta.flightbooking.service.FlightBookingAPI;
import com.yosta.flightbooking.service.FlightBookingAPIType;

import java.net.HttpURLConnection;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightActivity extends ActivityBehavior {


    @Override
    public void onApplyComponents() {
        super.onApplyComponents();
        setContentView(R.layout.activity_flight);
        ButterKnife.bind(this);

        FlightBookingAPI.getInstance(this).callRestFulAPI(
                FlightBookingAPIType.API_GET_DEPART_AIRPORT,
                new Callback<Airports>() {
                    @Override
                    public void onResponse(Call<Airports> call, Response<Airports> response) {
                        if (response.code() == HttpURLConnection.HTTP_OK) {
                            Airports airports = response.body();
                            String asda = "";
                        }
                    }

                    @Override
                    public void onFailure(Call<Airports> call, Throwable t) {
                        String assa = t.getMessage();
                    }
                });
    }

    @Override
    public void onApplyData() {
        super.onApplyData();

    }
}
