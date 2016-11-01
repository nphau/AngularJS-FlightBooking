package com.yosta.flightbooking;

import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.yosta.flightbooking.base.ActivityBehavior;
import com.yosta.flightbooking.config.AppConfig;
import com.yosta.flightbooking.databinding.ActivityFlightFindingBinding;
import com.yosta.flightbooking.helper.DatePickerFragment;
import com.yosta.flightbooking.helper.utils.AppUtils;
import com.yosta.flightbooking.helper.utils.SharedPresUtils;
import com.yosta.flightbooking.model.airport.Airports;
import com.yosta.flightbooking.model.flight.Flight;
import com.yosta.flightbooking.model.flight.Flights;
import com.yosta.flightbooking.networking.IFlightBookingAPI;
import com.yosta.materialdialog.ProgressDialog;
import com.yosta.materialdialog.TextInputDialog;
import com.yosta.materialspinner.MaterialSpinner;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FlightFindingActivity extends ActivityBehavior {

    @BindView(R.id.spinner_arrive)
    MaterialSpinner spinnerArrive;

    @BindView(R.id.spinner_depart)
    MaterialSpinner spinnerDepart;

    @BindView(R.id.rbOneWay)
    RadioButton rbOneWay;

    @BindView(R.id.layout_linear)
    LinearLayout layoutRoundTrip;

    private ProgressDialog progressDialog = null;
    private DatePickerFragment datePickerFragment;

    private Airports mDepartAirports = null, mArriveAirports = null;
    private final Flight mFlight = Flight.getDefault();

    @Inject
    SharedPresUtils sharedPresUtils;

    @Inject
    Retrofit retrofit;

    @Override
    public void onApplyComponents() {
        super.onApplyComponents();
        setContentView(R.layout.activity_flight_finding);

        ActivityFlightFindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_flight_finding);
        binding.setFlight(mFlight);
        ButterKnife.bind(this);
        ((AppConfig) getApplication()).getNetComponent().inject(this);

        datePickerFragment = new DatePickerFragment();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
    }

    @Override
    public void onApplyData() {
        super.onApplyData();

        spinnerDepart.setItems(Airports.getDefault());
        spinnerArrive.setItems(Airports.getDefault());

        onUpdateDepartAirport();
    }

    @Override
    public void onApplyEvents() {
        super.onApplyEvents();
        spinnerDepart.setOnItemSelectedListener(
                (view, position, id, item) -> {
                    onUpdateArriveAirport(mDepartAirports.getAirportId(position));
                });

        spinnerArrive.setOnItemSelectedListener(
                (view, position, id, item) ->
                        mFlight.setArriveAirport(mArriveAirports.getAirport(position)));

        rbOneWay.setOnCheckedChangeListener(
                (compoundButton, b) ->
                        layoutRoundTrip.setVisibility(b ? View.GONE : View.VISIBLE));
    }

    private void onUpdateDepartAirport() {
        Call<Airports> getDepartAirport = retrofit.create(IFlightBookingAPI.class).apiGetDepartAirport();
        getDepartAirport.enqueue(new Callback<Airports>() {
            @Override
            public void onResponse(Call<Airports> call, Response<Airports> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    mDepartAirports = response.body();
                    if (mDepartAirports.hasValue()) {
                        spinnerDepart.setItems(mDepartAirports.getList());
                        spinnerDepart.setSelectedIndex(0);
                    }
                }
            }

            @Override
            public void onFailure(Call<Airports> call, Throwable t) {
                Toast.makeText(FlightFindingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onUpdateArriveAirport(String departAirportId) {
        progressDialog.show();
        if (departAirportId != null && !TextUtils.isEmpty(departAirportId)) {
            Call<Airports> getArriveAirport = retrofit.create(IFlightBookingAPI.class).apiGetArriveAirport(departAirportId);
            getArriveAirport.enqueue(new Callback<Airports>() {
                @Override
                public void onResponse(Call<Airports> call, Response<Airports> response) {
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        mArriveAirports = response.body();
                        if (mArriveAirports.hasValue()) {
                            spinnerArrive.setItems(mArriveAirports.getList());
                            spinnerArrive.setSelectedIndex(0);
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<Airports> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void onFindNext(long departTime) {

        this.progressDialog.show();

        Call<Flights> getFlights = retrofit.create(IFlightBookingAPI.class)
                .apiFind(mFlight.getFlightParams());

        getFlights.enqueue(new Callback<Flights>() {
            @Override
            public void onResponse(Call<Flights> call, Response<Flights> response) {
                int code = response.code();
                switch (code) {
                    case HttpURLConnection.HTTP_OK: {
                        Flights flights = response.body();
                        sharedPresUtils.removeSettings(
                                SharedPresUtils.KEY_ARRIVE_TIME,
                                SharedPresUtils.KEY_DEPART_TIME);
                        flights.setArrive(mFlight.getArriveAirport());
                        flights.setDepart(mFlight.getDepartAirport());
                        progressDialog.dismiss();
                        AppUtils.sendObjectThroughBundle(
                                FlightFindingActivity.this,
                                BookActivity.class,
                                SharedPresUtils.KEY_FLIGHTS,
                                flights,
                                true);
                        break;
                    }
                    case HttpURLConnection.HTTP_BAD_GATEWAY: {
                        break;
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Flights> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.button_ok)
    public void onFind() {

        int arriveIndex = spinnerArrive.getSelectedIndex();
        int departIndex = spinnerDepart.getSelectedIndex();

        if (mArriveAirports == null || mDepartAirports == null || !mDepartAirports.hasValue() || !mArriveAirports.hasValue()) {
            return;
        }
        mFlight.setArriveAirport(mArriveAirports.getAirport(arriveIndex));
        mFlight.setDepartAirport(mDepartAirports.getAirport(departIndex));

        long departTime = this.sharedPresUtils.getSettingLong(SharedPresUtils.KEY_DEPART_TIME);
        long arriveTime = this.sharedPresUtils.getSettingLong(SharedPresUtils.KEY_ARRIVE_TIME);

        if (rbOneWay.isChecked() && departTime != 0) {
            onFindNext(departTime);
        } else {
            if (departTime != 0 || arriveTime != 0 || arriveTime > departTime) {
                onFindNext(departTime);
            }
        }
    }

    @OnClick({R.id.txt_date_depart, R.id.txt_date_arrive})
    public void onDatePicker(View view) {
        if (view.getId() == R.id.txt_date_depart) {
            datePickerFragment.showDate(getSupportFragmentManager(),
                    mFlight, SharedPresUtils.KEY_DEPART_TIME);
        }
        if (view.getId() == R.id.txt_date_arrive) {
            datePickerFragment.showDate(getSupportFragmentManager(),
                    mFlight, SharedPresUtils.KEY_ARRIVE_TIME);
        }
    }

    @OnClick({R.id.txt_adults, R.id.txt_children})
    public void onGetInput(View view) {
        if (view.getId() == R.id.txt_adults) {
            new TextInputDialog(this, R.style.EditTextTintTheme)
                    .setTopColorRes(R.color.DarkRed)
                    .setTitle("Adults")
                    .setTopTitle("")
                    .setMessage("How many tickets you want to book?")
                    .setConfirmButton(android.R.string.ok, text -> {
                        int maxAdults = Integer.parseInt(text);
                        mFlight.setAdults(maxAdults);
                        mFlight.setChildren(maxAdults * 2);
                    })
                    .setInputFilter("6 adults are maximum.", text -> {
                        boolean res = false;
                        if (text.matches("\\d+")) {
                            int maxAdults = Integer.parseInt(text);
                            if (maxAdults > 0 && maxAdults < 7) {
                                res = true;
                            }
                        }
                        return res;
                    }).show();
        }
        if (view.getId() == R.id.txt_children) {
            new TextInputDialog(this, R.style.EditTextTintTheme)
                    .setTopColorRes(R.color.Red)
                    .setTitle("Children")
                    .setTopTitle("")
                    .setMessage("How many tickets you want to book?")
                    .setConfirmButton(android.R.string.ok, text -> {
                        int maxChildren = Integer.parseInt(text);
                        mFlight.setChildren(maxChildren);
                    })
                    .setInputFilter((mFlight.getAdults() * 2) + " is maximum.", text -> {
                        boolean res = false;
                        if (text.matches("\\d+")) {
                            int maxChildren = Integer.parseInt(text);
                            res = (maxChildren >= 0 && maxChildren <= mFlight.getAdults() * 2);
                        }
                        return res;
                    }).show();
        }
    }
}

