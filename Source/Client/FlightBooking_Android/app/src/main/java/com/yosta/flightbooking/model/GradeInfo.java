package com.yosta.flightbooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public class GradeInfo implements Serializable {

    @SerializedName("grade")
    private String mGrade;

    @SerializedName("number")
    private int mNumber;

    @SerializedName("price")
    private int mPrice;

    public GradeInfo() {
    }

    public GradeInfo(String mGrade, int mNumber, int mPrice) {
        this.mGrade = mGrade;
        this.mNumber = mNumber;
        this.mPrice = mPrice;
    }

    public String getGrade() {
        return mGrade;
    }

    public int getNumber() {
        return mNumber;
    }

    public int getPrice() {
        return mPrice;
    }
}
