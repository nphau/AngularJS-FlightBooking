package com.yosta.flightbooking.helper;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Phuc-Hau Nguyen on 10/23/2016.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private TextView mTime;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = " AM";
        if (hourOfDay > 12) {
            hourOfDay -= 12;
            time = " PM";
        }
        time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Time(hourOfDay, minute, 0)) + time;
        this.mTime.setText(time);
    }

    public void showTime(FragmentManager manager, TextView txtOut) {
        super.show(manager, "timePicker");
        this.mTime = txtOut;
    }
}
