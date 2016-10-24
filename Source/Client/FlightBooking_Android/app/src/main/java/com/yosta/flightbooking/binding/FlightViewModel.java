package com.yosta.flightbooking.binding;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.yosta.flightbooking.model.Flight;

/**
 * Created by Phuc-Hau Nguyen on 10/24/2016.
 */

public class FlightViewModel extends BaseObservable {

    private Context mContext = null;
    private Flight mFlight = null;
    private String mDepartName = null;
    private String mArriveName = null;

    public FlightViewModel(Context context, Flight flight, String depart, String arrive) {
        this.mContext = context;
        this.mFlight = flight;
        this.mDepartName = depart;
        this.mArriveName = arrive;
    }

    @Bindable
    public String getFlightID() {
        return mFlight.getFlightId();
    }

    @Bindable
    public String getArrive() {
        return this.mArriveName;
    }

    @Bindable
    public String getDepart() {
        return this.mDepartName;
    }

    @Bindable
    public String getDepartDate() {
        return mFlight.getDepartDate();
    }
    public String getDepartTime() {
        return mFlight.getDepartTime();
    }
}
