package com.yosta.flightbooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public class Depart implements Serializable {
    @SerializedName("depart")
    private String mDepart = null;

    public Depart(String depart) {
        this.mDepart = depart;
    }
}
