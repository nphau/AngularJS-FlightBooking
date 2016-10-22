package com.yosta.flightbooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public class Airports implements Serializable {

    SerializedName("departs")

    List<Airport> airportList = new ArrayList<>();

}
