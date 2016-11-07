package com.yosta.flightbooking.binding.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.yosta.flightbooking.binding.field.ObservableString;
import com.yosta.flightbooking.model.flight.Flight;

/**
 * Created by Phuc-Hau Nguyen on 10/24/2016.
 */

public class FlightVM extends BaseObservable {

    private Flight mFlight = null;
    private String mDepartName = null;
    private String mArriveName = null;

    /*public final ObservableString children = new ObservableString();
    public final ObservableString adults = new ObservableString();*/

    public FlightVM(Flight mFlight) {
        this.mFlight = mFlight;
        /*this.children.set(this.mFlight.getChildren());
        this.adults.set(this.mFlight.getAdults());*/
    }

    public FlightVM(Flight mFlight, String mDepartName, String mArriveName) {
        this.mFlight = mFlight;
        this.mDepartName = mDepartName;
        this.mArriveName = mArriveName;
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
        return mFlight.getDepartTime();
    }

    public String getDepartTime() {
        return mFlight.getDepartTime();
    }


    public String children() {
        return String.valueOf(this.mFlight.getChildren());
    }
    public String adults() {
        return String.valueOf(this.mFlight.getAdults());
    }
}
