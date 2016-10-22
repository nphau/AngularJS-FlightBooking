package com.yosta.flightbooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public class Flight implements Serializable {

    @SerializedName("flightId")
    private String mFlightId;

    @SerializedName("depart")
    private String mDepart;

    @SerializedName("arrive")
    private String mArrive;

    @SerializedName("time")
    private String mDatetime;

    @SerializedName("flex")
    private ClassTypes mClassTypes;


}
