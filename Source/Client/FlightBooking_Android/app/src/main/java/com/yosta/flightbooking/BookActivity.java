package com.yosta.flightbooking;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yosta.flightbooking.adapter.GradeInfoAdapter;
import com.yosta.flightbooking.binding.FlightViewModel;
import com.yosta.flightbooking.databinding.ActivityBookBinding;
import com.yosta.flightbooking.helper.AppUtils;
import com.yosta.flightbooking.model.Flight;
import com.yosta.flightbooking.model.Flights;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Flights flights = null;
    private GradeInfoAdapter gradeInfoAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBookBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_book);
        ButterKnife.bind(this);
        gradeInfoAdapter = new GradeInfoAdapter(this);
        onApplyRecyclerView();
        flights = (Flights) AppUtils.receiveDataThroughBundle(this, "FLIGHTS");
        if (flights != null) {
            gradeInfoAdapter.clear();
            Flight flight = flights.get(0);
            gradeInfoAdapter.addGrades(flight.getGradeInfo());
            binding.setFlight(new FlightViewModel(this, flight,
                    flights.getDepart().getName(), flights.getArrive().getName()));
        }

    }

    private void onApplyRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHorizontalScrollBarEnabled(true);
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(gradeInfoAdapter);
    }
}
