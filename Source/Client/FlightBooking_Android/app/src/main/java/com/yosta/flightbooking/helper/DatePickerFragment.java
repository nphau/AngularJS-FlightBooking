package com.yosta.flightbooking.helper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Phuc-Hau Nguyen on 10/23/2016.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private TextView mDate;
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
        calendar.set(year, month, date);
        String formattedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendar.getTime());
        mDate.setText(formattedDate);
        long time = DateUtils.convertDateIntoTimestamp(calendar.getTime());
        this.sharedPresUtils.saveSetting(this.mType, time);
    }

    public void showDate(FragmentManager manager, TextView txtOut, String type) {
        super.show(manager, "datePicker");
        this.mType = type;
        this.mDate = txtOut;
    }
}
