package com.yosta.flightbooking.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yosta.flightbooking.helper.DateUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public class Flight implements Serializable {

    @SerializedName("flightId")
    private String mFlightId = null;

    @SerializedName("flex")
    private List<GradeInfo> classTypes;

    @SerializedName("time")
    private long mTime;

    public Flight() {
        this.mFlightId = null;
        this.classTypes = new ArrayList<>();
    }

    public List<GradeInfo> getGradeInfo() {
        return classTypes;
    }

    public String getFlightId() {
        return this.mFlightId;
    }
    public String getDepartDate(){
        return DateUtils.convertTimeStamp2Date(this.mTime);
    }
    public String getDepartTime(){
        return DateUtils.convertTimeStamp2Time(this.mTime);
    }
}
