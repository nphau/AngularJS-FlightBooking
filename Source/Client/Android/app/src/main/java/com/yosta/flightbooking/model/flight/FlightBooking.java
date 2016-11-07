package com.yosta.flightbooking.model.flight;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yosta.flightbooking.model.airport.Airport;

import java.io.Serializable;

/**
 * Created by Phuc-Hau Nguyen on 10/24/2016.
 */

public class FlightBooking implements Serializable {

    @SerializedName("flightId")
    private String mFlightId;

    @SerializedName("time")
    private String mTime;

    @SerializedName("grade")
    private String mGrade;

    @SerializedName("price")
    private int mPrice;

    @Expose(deserialize = false, serialize = false)
    private Airport mDepart;

    @Expose(deserialize = false, serialize = false)
    private Airport mArrive;

    public FlightBooking(String flightId, String time, String grade, int price) {
        this.mFlightId = flightId;
        this.mTime = time;
        this.mGrade = grade;
        this.mPrice = price;
    }

    public FlightBooking(String mFlightId, String mTime, String mGrade, int mPrice, Airport mDepart, Airport mArrive) {
        this.mFlightId = mFlightId;
        this.mTime = mTime;
        this.mGrade = mGrade;
        this.mPrice = mPrice;
        this.mDepart = mDepart;
        this.mArrive = mArrive;
    }

    public void setFlightId(String mFlightId) {
        this.mFlightId = mFlightId;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }

    public void setGrade(String mGrade) {
        this.mGrade = mGrade;
    }

    public void setPrice(int price) {
        this.mPrice = price;
    }

    public String getFlightId() {
        return mFlightId;
    }

    public String getTime() {
        return this.mTime;
    }

    public String getGrade() {
        return mGrade;
    }

    public int getPrice() {
        return mPrice;
    }

    public Airport getDepart() {
        return mDepart;
    }

    public Airport getArrive() {
        return mArrive;
    }

    @Override
    public String toString() {
        return "Flight: " + this.mFlightId + "Time: " + this.mTime + "Grade: " + this.mGrade;
    }
}
