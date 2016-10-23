package com.yosta.flightbooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public class Flights implements Serializable {
    @SerializedName("flights")
    private List<Flight> flights = new ArrayList<>();

}
