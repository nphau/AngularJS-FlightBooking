package com.yosta.flightbooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public class TheFlight implements Serializable {

    @SerializedName("flightId")
    private String mFlightId;

    @SerializedName("time")
    private long mDataTime;

    @SerializedName("grade")
    private String mGrade;
/*
    @SerializedName("price ")
    private Cost price;*/
}
