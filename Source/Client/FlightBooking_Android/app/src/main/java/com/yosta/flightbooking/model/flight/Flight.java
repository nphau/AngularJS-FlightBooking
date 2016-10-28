package com.yosta.flightbooking.model.flight;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yosta.flightbooking.helper.utils.DateUtils;
import com.yosta.flightbooking.model.GradeInfo;
import com.yosta.flightbooking.model.airport.Airport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public class Flight extends BaseObservable implements Serializable {

    @SerializedName("flightId")
    private String mFlightId = null;

    @SerializedName("flex")
    private List<GradeInfo> classTypes;

    @SerializedName("time")
    private long mDepartTime = 0L;
    private long mArriveTime = 0L;

    @Expose(deserialize = false, serialize = false)
    private int mAdults = 1;

    @Expose(deserialize = false, serialize = false)
    private int mChildren = 0;

    private Airport mDepartAirport = null, mArriveAirport = null;

    public Flight() {
        this.mFlightId = null;
        this.classTypes = new ArrayList<>();
        this.mAdults = 1;
        this.mChildren = 0;
        this.mDepartTime = Calendar.getInstance().getTime().getTime();
        this.mArriveTime = Calendar.getInstance().getTime().getTime();
    }

    public List<GradeInfo> getGradeInfo() {
        return classTypes;
    }

    public String getFlightId() {
        return this.mFlightId;
    }

    @Bindable
    public String getDepartTime() {
        return DateUtils.convertTimeStamp2Date(this.mDepartTime);
    }

    @Bindable
    public String getArriveTime() {
        return DateUtils.convertTimeStamp2Date(this.mArriveTime);
    }

    @Bindable
    public String getChildrenString() {
        return String.valueOf(this.mChildren);
    }

    @Bindable
    public String getAdultsString() {
        return String.valueOf(this.mAdults);
    }

    public int getChildren() {
        return this.mChildren;
    }

    public int getAdults() {
        return this.mAdults;
    }

    public void setDepartTime(long time) {
        this.mDepartTime = time;
        notifyChange();
    }

    public void setArriveTime(long time) {
        this.mArriveTime = time;
        notifyChange();
    }

    public void setChildren(int mChildren) {
        this.mChildren = mChildren;
        notifyChange();
    }

    public void setAdults(int mAdults) {
        this.mAdults = mAdults;
        notifyChange();
    }

    public static Flight getDefault() {
        return new Flight();
    }

    public Airport getDepartAirport() {
        return mDepartAirport;
    }

    public void setDepartAirport(Airport mDepartAirport) {
        this.mDepartAirport = mDepartAirport;
    }

    public Airport getArriveAirport() {
        return mArriveAirport;
    }

    public void setArriveAirport(Airport mArriveAirport) {
        this.mArriveAirport = mArriveAirport;
    }

    public Map<String, String> getFlightParams() {

        Map<String, String> params = new HashMap<>();

        params.put("depart", mDepartAirport.getId());
        //params.put("arrive", mArriveAirport.getId());
        params.put("time", String.valueOf(mDepartTime));
        params.put("adult", String.valueOf(mAdults));
        params.put("child", String.valueOf(mChildren));

        return params;
    }
}
