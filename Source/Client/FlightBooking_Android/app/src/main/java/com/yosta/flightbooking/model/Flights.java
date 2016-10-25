package com.yosta.flightbooking.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public class Flights implements Serializable {

    @SerializedName("flights")
    private List<Flight> mFlights = new ArrayList<>();

    @Expose(serialize = false, deserialize = false)
    private Airport mDepart = null;

    @Expose(serialize = false, deserialize = false)
    private Airport mArrive = null;


    public Airport getDepart() {
        return mDepart;
    }

    public Airport getArrive() {
        return mArrive;
    }

    public void setDepart(Airport depart) {
        this.mDepart = depart;
    }

    public void setArrive(Airport arrive) {
        this.mArrive = arrive;
    }

    public Flight get(int index) {
        return mFlights.get(index);
    }

    public List<GradeInfo> getGradeInfo(int index) {
        return mFlights.get(index).getGradeInfo();
    }

    public List<Flight> getFlights() {
        return this.mFlights;
    }

    public int size() {
        return mFlights.size();
    }
}
