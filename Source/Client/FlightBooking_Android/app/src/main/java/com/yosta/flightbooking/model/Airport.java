package com.yosta.flightbooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public class Airport implements Serializable {

    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;

    public Airport() {

    }

    public Airport(String id, String name) {
        this.mId = id;
        this.mName = name;
    }

    public String getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    @Override
    public String toString() {
        return "(" + mId + ")" + mName;
    }
}
