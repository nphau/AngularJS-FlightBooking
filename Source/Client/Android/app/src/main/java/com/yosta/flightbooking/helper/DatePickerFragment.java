package com.yosta.flightbooking.helper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;

import com.yosta.flightbooking.helper.utils.DateUtils;
import com.yosta.flightbooking.helper.utils.SharedPresUtils;
import com.yosta.flightbooking.model.flight.Flight;
import java.util.Calendar;
/**
 * Created by Phuc-Hau Nguyen on 10/23/2016.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Flight mFlight;
    private SharedPresUtils sharedPresUtils = null;
    private String mType;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        this.sharedPresUtils = new SharedPresUtils(getActivity());

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date, 0, 0, 0);
        long time = calendar.getTimeInMillis();
        switch (this.mType) {
            case SharedPresUtils.KEY_DEPART_TIME: {
                this.mFlight.setDepartTime(time);
                break;
            }
            case SharedPresUtils.KEY_ARRIVE_TIME: {
                this.mFlight.setArriveTime(time);
                break;
            }
        }
        this.sharedPresUtils.saveSetting(this.mType, time);
    }

    public void showDate(FragmentManager manager, Flight flight, String type) {
        super.show(manager, "datePicker");
        this.mType = type;
        this.mFlight = flight;
    }
}
