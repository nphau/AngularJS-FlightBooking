package com.yosta.flightbooking.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public class Airports implements Serializable {

    @SerializedName("airports")
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

    public String getAirportId(int index) {
        String res = null;
        int min = 0, max = airports.size();
        if (hasValue() && index > min && index < max)
            res = airports.get(index).getId();
        return res;
    }

    public Airport getAirport(int index) {
        return airports.get(index);
    }

    public String getAirportName(int index) {
        String res = null;
        int min = 0, max = airports.size();
        if (hasValue() && index > min && index < max)
            res = airports.get(index).getName();
        return res;
    }

    public static List<String> getDefault() {
        List<String> res = new ArrayList<>();

        res.add(new Airport("HAN", "Hà Nội").toString());
        res.add(new Airport("SGN", "Tp Hồ Chí Minh").toString());

        return res;
    }
}
