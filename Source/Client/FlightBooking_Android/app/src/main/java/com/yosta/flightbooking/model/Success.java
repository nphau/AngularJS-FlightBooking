package com.yosta.flightbooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Phuc-Hau Nguyen on 10/24/2016.
 */

public class Success implements Serializable {

    @SerializedName("success")
    private boolean mSuccess = false;


    public boolean isSuccess() {
        return mSuccess;
    }

}
