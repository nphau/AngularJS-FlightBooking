package com.yosta.flightbooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public class Airport implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    @Override
    public String toString() {
        return "(" + id + ")" + name;
    }
}
