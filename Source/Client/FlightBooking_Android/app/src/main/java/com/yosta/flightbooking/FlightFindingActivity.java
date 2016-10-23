package com.yosta.flightbooking;

import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.yosta.flightbooking.base.ActivityBehavior;
import com.yosta.flightbooking.helper.DatePickerFragment;
import com.yosta.flightbooking.helper.TimePickerFragment;
import com.yosta.flightbooking.model.Airports;
import com.yosta.flightbooking.model.Flights;
import com.yosta.flightbooking.service.FlightBookingAPI;
import com.yosta.flightbooking.service.FlightBookingAPIType;
import com.yosta.materialdialog.ProgressDialog;
import com.yosta.materialdialog.TextInputDialog;
import com.yosta.materialspinner.MaterialSpinner;

import java.net.HttpURLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightFindingActivity extends ActivityBehavior {

    @BindView(R.id.spinner_arrive)
    MaterialSpinner spinnerArrive;

    @BindView(R.id.spinner_depart)
    MaterialSpinner spinnerDepart;

    @BindView(R.id.txt_date_arrive)
    AppCompatTextView txtDateArrive;

    @BindView(R.id.txt_date_depart)
    AppCompatTextView txtDateDepart;

    @BindView(R.id.txt_time_arrive)
    AppCompatTextView txtTimeArrive;

    @BindView(R.id.txt_time_depart)
    AppCompatTextView txtTimeDepart;

    @BindView(R.id.txt_adults)
    AppCompatTextView txtAdults;

    @BindView(R.id.txt_children)
    AppCompatTextView txtChildren;

    @BindView(R.id.rbOneWay)
    RadioButton rbOneWay;

    @BindView(R.id.rbRoundTrip)
    RadioButton rbRoundTrip;

    @BindView(R.id.layout_linear)
    LinearLayout layoutRoundTrip;

    private Airports departAirports = null;
    private Airports arriveAirports = null;
    private ProgressDialog progressDialog = null;
    private DatePickerFragment datePickerFragment;
    private TimePickerFragment timePickerFragment;

    private int maxAdults = 1, maxChildren = 0;

    @Override
    public void onApplyComponents() {
        super.onApplyComponents();
        setContentView(R.layout.activity_flight_finding);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        //
        departAirports = new Airports();
        arriveAirports = new Airports();

        datePickerFragment = new DatePickerFragment();
        timePickerFragment = new TimePickerFragment();
    }

    @Override
    public void onApplyData() {
        super.onApplyData();

        spinnerDepart.setItems(Airports.getDefault());
        spinnerArrive.setItems(Airports.getDefault());
        txtAdults.setText(String.valueOf(maxAdults));
        txtChildren.setText(String.valueOf(maxChildren));

        onUpdateTime();
        onUpdateDepartAirport();
    }

    private void onUpdateTime() {

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
        String formattedTime = new SimpleDateFormat("HH:mm:ss").format(date);

        txtDateDepart.setText(formattedDate);
        txtDateArrive.setText(formattedDate);

        txtTimeDepart.setText(formattedTime);
        txtTimeArrive.setText(formattedTime);

    }

    @Override
    public void onApplyEvents() {
        super.onApplyEvents();
        spinnerDepart.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                onUpdateArriveAirport(departAirports.getAirportId(position));
            }
        });

        rbOneWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbOneWay.toggle();
                if (rbOneWay.isChecked()) {
                    rbRoundTrip.setChecked(false);
                    layoutRoundTrip.setVisibility(View.GONE);
                } else {
                    rbRoundTrip.setChecked(true);
                    layoutRoundTrip.setVisibility(View.VISIBLE);
                }
            }
        });
        rbRoundTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbRoundTrip.toggle();
                if (rbRoundTrip.isChecked()) {
                    rbOneWay.setChecked(false);
                    layoutRoundTrip.setVisibility(View.VISIBLE);
                } else {
                    rbOneWay.setChecked(true);
                    layoutRoundTrip.setVisibility(View.GONE);
                }
            }
        });
    }

    private void onUpdateDepartAirport() {
        progressDialog.show();
        FlightBookingAPI.getInstance(this).callRestFulAPI(
                FlightBookingAPIType.API_GET_DEPART_AIRPORT,
                new Callback<Airports>() {
                    @Override
                    public void onResponse(Call<Airports> call, Response<Airports> response) {
                        if (response.code() == HttpURLConnection.HTTP_OK) {
                            departAirports = response.body();
                            if (departAirports.hasValue()) {
                                spinnerDepart.setItems(departAirports.getList());
                            }
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Airports> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(FlightFindingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void onUpdateArriveAirport(String departAirportId) {
        progressDialog.show();
        if (departAirportId != null && !TextUtils.isEmpty(departAirportId)) {
            FlightBookingAPI.getInstance(this).callRestFulAPI(
                    FlightBookingAPIType.API_GET_ARRIVE_AIRPORT,
                    new Callback<Airports>() {
                        @Override
                        public void onResponse(Call<Airports> call, Response<Airports> response) {
                            if (response.code() == HttpURLConnection.HTTP_OK) {
                                arriveAirports = response.body();
                                if (arriveAirports.hasValue()) {
                                    spinnerArrive.setItems(arriveAirports.getList());
                                }
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Airports> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(FlightFindingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }, departAirportId);
        }
    }

    @OnClick(R.id.button_ok)
    public void onFind() {
        this.progressDialog.show();
        Calendar calendar = Calendar.getInstance();
        Map<String, String> params = new HashMap<>();
        params.put("depart", "SGN");
        params.put("arrive", "TBB");
        params.put("time", String.valueOf(calendar.getTime().getTime()));
        params.put("adult", "2");
        params.put("child", "0");

        FlightBookingAPI.getInstance(this).callAPIFind(params, new Callback<Flights>() {
            @Override
            public void onResponse(Call<Flights> call, Response<Flights> response) {
                progressDialog.dismiss();
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Flights flights = response.body();

                }
            }

            @Override
            public void onFailure(Call<Flights> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(FlightFindingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.txt_date_depart, R.id.txt_date_arrive})
    public void onDatePicker(View view) {
        if (view.getId() == R.id.txt_date_depart) {
            datePickerFragment.showDate(getSupportFragmentManager(), txtDateDepart);
        }
        if (view.getId() == R.id.txt_date_arrive) {
            datePickerFragment.showDate(getSupportFragmentManager(), txtDateArrive);
        }
    }

    @OnClick({R.id.txt_time_depart, R.id.txt_time_arrive})
    public void onTimePicker(View view) {
        if (view.getId() == R.id.txt_time_depart) {
            timePickerFragment.showTime(getSupportFragmentManager(), txtTimeDepart);
        }
        if (view.getId() == R.id.txt_time_arrive) {
            timePickerFragment.showTime(getSupportFragmentManager(), txtTimeArrive);
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
                    .setConfirmButton(android.R.string.ok, new TextInputDialog.OnTextInputConfirmListener() {
                        @Override
                        public void onTextInputConfirmed(String text) {
                            txtAdults.setText(String.valueOf(maxAdults));
                            txtChildren.setText(String.valueOf(maxAdults * 2));
                        }

                    })
                    .setInputFilter("6 adults are maximum.", new TextInputDialog.TextFilter() {
                        @Override
                        public boolean check(String text) {
                            boolean res = false;
                            if (text.matches("\\d+")) {
                                maxAdults = Integer.parseInt(text);
                                if (maxAdults > 0 && maxAdults < 7) {
                                    res = true;
                                }
                            }
                            return res;
                        }
                    }).show();
                /*.
                .setIcon(R.drawable.ic_assignment_white_36dp)
                )*/
        }
        if (view.getId() == R.id.txt_children) {
            new TextInputDialog(this, R.style.EditTextTintTheme)
                    .setTopColorRes(R.color.Red)
                    .setTitle("Children")
                    .setTopTitle("")
                    .setMessage("How many tickets you want to book?")
                    .setConfirmButton(android.R.string.ok, new TextInputDialog.OnTextInputConfirmListener() {
                        @Override
                        public void onTextInputConfirmed(String text) {
                            txtChildren.setText(String.valueOf(maxChildren));
                        }

                    })
                    .setInputFilter((maxAdults * 2) + " is maximum.", new TextInputDialog.TextFilter() {
                        @Override
                        public boolean check(String text) {
                            boolean res = false;
                            if (text.matches("\\d+")) {
                                maxChildren = Integer.parseInt(text);
                                if (maxChildren >= 0 && maxChildren <= maxAdults * 2) {
                                    res = true;
                                }
                            }
                            return res;
                        }
                    }).show();
        }
    }
}

