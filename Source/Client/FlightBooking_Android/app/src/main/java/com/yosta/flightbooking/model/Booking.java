package com.yosta.flightbooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public class Booking implements Serializable {

    @SerializedName("bookingId")
    private String mBookingId;

    @SerializedName("time")
    private long mTime;

    /*@SerializedName("totalCost")
    private Cost mTotalCost;*/

    @SerializedName("status")
    private boolean mStatus;

}
