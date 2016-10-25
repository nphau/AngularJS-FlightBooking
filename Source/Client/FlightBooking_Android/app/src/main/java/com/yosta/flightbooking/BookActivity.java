package com.yosta.flightbooking;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.yosta.flightbooking.adapter.GradeInfoAdapter;
import com.yosta.flightbooking.binding.FlightViewModel;
import com.yosta.flightbooking.databinding.ActivityBookBinding;
import com.yosta.flightbooking.dialog.DialogFlight;
import com.yosta.flightbooking.helper.AppUtils;
import com.yosta.flightbooking.helper.ItemClickSupport;
import com.yosta.flightbooking.helper.SharedPresUtils;
import com.yosta.flightbooking.model.Booking;
import com.yosta.flightbooking.model.Flight;
import com.yosta.flightbooking.model.FlightBooking;
import com.yosta.flightbooking.model.Flights;
import com.yosta.flightbooking.model.GradeInfo;
import com.yosta.flightbooking.model.Success;
import com.yosta.flightbooking.service.FlightBookingAPI;
import com.yosta.materialdialog.ProgressDialog;

import java.net.HttpURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    private Flights mFlights = null;
    private String mBookingId = null;
    private Flight mCurrentFlight = null;
    private GradeInfo mCurrGrade = null;
    private SharedPresUtils sharedPresUtils = null;
    private GradeInfoAdapter gradeInfoAdapter = null;
    private ActivityBookBinding mBinding = null;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mBinding = DataBindingUtil.setContentView(this, R.layout.activity_book);
        ButterKnife.bind(this);
        this.mFlights = new Flights();
        this.sharedPresUtils = new SharedPresUtils(this);
        this.gradeInfoAdapter = new GradeInfoAdapter(this);
        progressDialog = new ProgressDialog(this);
        onApplyRecyclerView();
        onApplyData();
    }

    private void onApplyData() {
        this.mFlights = (Flights) AppUtils.receiveDataThroughBundle(this, "FLIGHTS");
        if (mFlights != null) {
            gradeInfoAdapter.clear();
            mCurrentFlight = mFlights.get(0);
            gradeInfoAdapter.addGrades(mCurrentFlight.getGradeInfo());
            this.mBinding.setFlight(new FlightViewModel(this, mCurrentFlight, mFlights.getDepart().getName(), mFlights.getArrive().getName()));

            onGetBookingId();
        }
    }

    private void onGetBookingId() {
        mBookingId = sharedPresUtils.getSettingString(SharedPresUtils.KEY_BOOKING_ID);
        if (mBookingId == null || TextUtils.isEmpty(mBookingId)) {
            // Get Booking ID
            FlightBookingAPI.getInstance(this).callAPIBooking(new Callback<Booking>() {
                @Override
                public void onResponse(Call<Booking> call, Response<Booking> response) {
                    if (response.code() == HttpURLConnection.HTTP_CREATED) {
                        Booking booking = response.body();
                        sharedPresUtils.saveSetting(SharedPresUtils.KEY_BOOKING_ID, booking.toString());
                        mBookingId = booking.toString();
                    }
                }

                @Override
                public void onFailure(Call<Booking> call, Throwable t) {
                    Toast.makeText(BookActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void onApplyRecyclerView() {
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setHorizontalScrollBarEnabled(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(gradeInfoAdapter);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        if (position >= 0 && position < gradeInfoAdapter.getItemCount()) {
                            mCurrGrade = mCurrentFlight.getGradeInfo().get(position);

                            final FlightBooking flightBooking = new FlightBooking(
                                    mBookingId,
                                    mCurrentFlight.getDepartTime(),
                                    mCurrGrade.getGrade(),
                                    mCurrGrade.getPrice(),
                                    mFlights.getDepart(),
                                    mFlights.getArrive()
                            );
                            SharedPresUtils.saveSetting(BookActivity.this, SharedPresUtils.KEY_BOOKING_FLIGHT, flightBooking);

                            new AlertDialog.Builder(BookActivity.this)
                                    .setTitle("Do you want to book this flight?")
                                    .setCancelable(false)
                                    .setMessage(flightBooking.toString())
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            progressDialog.show();
                                            FlightBookingAPI.getInstance(BookActivity.this)
                                                    .callAPIBookingFlight(mBookingId, flightBooking, bFCallback);
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();

                        }
                    }
                }
        );
    }

    private Callback<Success> bFCallback = new Callback<Success>() {
        @Override
        public void onResponse(Call<Success> call, Response<Success> response) {
            int code = response.code();
            switch (code) {
                case HttpURLConnection.HTTP_CREATED: {
                    if (response.body().isSuccess()) {
                        sharedPresUtils.removeSettings(SharedPresUtils.KEY_BOOKING_ID);
                        DialogFlight dialogFlight = new DialogFlight(BookActivity.this);
                        dialogFlight.show();
                    }
                    break;
                }
                case HttpURLConnection.HTTP_SERVER_ERROR: {
                    Toast.makeText(BookActivity.this, "Lost connection", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            progressDialog.dismiss();
        }

        @Override
        public void onFailure(Call<Success> call, Throwable t) {
            Toast.makeText(BookActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    };

}
