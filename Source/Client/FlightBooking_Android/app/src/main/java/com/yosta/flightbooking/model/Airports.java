package com.yosta.flightbooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public class Airports implements Serializable {

    @SerializedName("depart_airports")
    private List<Airport> airports = new ArrayList<>();

    public List<String> getList() {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < airports.size(); i++) {
            res.add(airports.get(i).toString());
        }
        return res;
    }

    public boolean hasValue() {
        return (airports != null && airports.size() > 0);
    }
}
