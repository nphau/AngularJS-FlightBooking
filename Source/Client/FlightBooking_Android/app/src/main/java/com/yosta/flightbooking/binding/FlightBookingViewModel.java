package com.yosta.flightbooking.binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.yosta.flightbooking.model.FlightBooking;

/**
 * Created by Phuc-Hau Nguyen on 10/24/2016.
 */

public class FlightBookingViewModel extends BaseObservable {

    private FlightBooking mFlightBooking = null;

    public FlightBookingViewModel(FlightBooking mFlightBooking) {
        this.mFlightBooking = mFlightBooking;
    }

    @Bindable
    public String getFlightID() {
        return "FlightID: " + mFlightBooking.getFlightId();
    }

    @Bindable
    public String getArriveId() {
        return this.mFlightBooking.getArrive().getId();
    }

    @Bindable
    public String getArriveName() {
        return  this.mFlightBooking.getArrive().getName();
    }

    @Bindable
    public String getCost() {
        return "Price: " + String.valueOf(this.mFlightBooking.getPrice());
    }

    @Bindable
    public String getGrade() {
        return "Grade: " + this.mFlightBooking.getGrade();
    }

    @Bindable
    public String getDepartId() {
        return this.mFlightBooking.getDepart().getId();
    }

    @Bindable
    public String getDepartName() {
        return this.mFlightBooking.getDepart().getName();
    }

    public String getTime() {
        return "Time: " + this.mFlightBooking.getTime();
    }
}
