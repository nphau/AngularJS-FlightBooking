package com.yosta.flightbooking.model.airport;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public class Airports implements Serializable {

    @SerializedName("airports")
    private List<Airport> mAirports = new ArrayList<>();

    public List<String> getList() {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < mAirports.size(); i++) {
            res.add(mAirports.get(i).toString());
        }
        return res;
    }

    public boolean hasValue() {
        return (mAirports != null && mAirports.size() > 0);
    }

    public String getAirportId(int index) {
        String res = null;
        int min = 0, max = mAirports.size();
        if (hasValue() && index > min && index < max)
            res = mAirports.get(index).getId();
        return res;
    }

    public Airport getAirport(int index) {
        return mAirports.get(index);
    }

    public String getAirportName(int index) {
        String res = null;
        int min = 0, max = mAirports.size();
        if (hasValue() && index > min && index < max)
            res = mAirports.get(index).getName();
        return res;
    }

    public static List<String> getDefault() {
        List<String> res = new ArrayList<>();

        res.add(new Airport("HAN", "Hà Nội").toString());
        res.add(new Airport("SGN", "Tp Hồ Chí Minh").toString());

        return res;
    }
    public void add(Airport airport){
        mAirports.add(airport);
    }
}
