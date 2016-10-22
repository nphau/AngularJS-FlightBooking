package com.yosta.flightbooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public class ClassType implements Serializable {

    @SerializedName("grade")
    private String mGrade;

    @SerializedName("number")
    private int mNumber;

    @SerializedName("price")
    private Cost mPrice;


}
