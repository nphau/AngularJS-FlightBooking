package com.yosta.flightbooking;

import com.yosta.flightbooking.base.ActivityBehavior;
import com.yosta.flightbooking.model.Airports;
import com.yosta.flightbooking.service.FlightBookingAPI;
import com.yosta.flightbooking.service.FlightBookingAPIType;
import com.yosta.materialspinner.MaterialSpinner;

import java.net.HttpURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightActivity extends ActivityBehavior {


    @BindView(R.id.spinner_arrive)
    MaterialSpinner spinnerArrive;

    @BindView(R.id.spinner_depart)
    MaterialSpinner spinnerDepart;

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
                            if (airports.hasValue()) {
                                spinnerDepart.setItems(airports.getList());
                            }
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

        spinnerDepart.setItems("a", "b", "c");
    }
}
