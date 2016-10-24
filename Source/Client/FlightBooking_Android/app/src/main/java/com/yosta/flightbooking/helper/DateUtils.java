package com.yosta.flightbooking.helper;

import android.text.format.DateFormat;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Phuc-Hau Nguyen on 10/24/2016.
 */

public class DateUtils {

    public static String convertTimeStamp2Date(long microTime) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(microTime);
        return DateFormat.format("dd-MM-yyyy", cal).toString();
    }
    public static String convertTimeStamp2Time(long microTime) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(microTime);
        return DateFormat.format("HH:mm", cal).toString();
    }
    public static long convertDateIntoTimestamp(Date date) {
        Timestamp currentTimestamp = new Timestamp(date.getTime());
        return currentTimestamp.getTime();
    }
}
