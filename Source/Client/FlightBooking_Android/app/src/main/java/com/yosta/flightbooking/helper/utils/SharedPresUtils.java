package com.yosta.flightbooking.helper.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import com.google.gson.Gson;
import com.yosta.flightbooking.model.flight.FlightBooking;

import java.util.Locale;

/**
 * Created by nphau on 9/27/2015.
 */
public class SharedPresUtils {

    public static final String KEY_FLIGHTS = "FLIGHTS";
    public static final String KEY_DEPART_TIME = "KEY_DEPART_TIME";
    public static final String KEY_ARRIVE_TIME = "KEY_ARRIVE_TIME";
    public static final String KEY_BOOKING_ID = "KEY_BOOKING_ID";
    public static final String KEY_BOOKING_FLIGHT = "KEY_BOOKING_FLIGHT";
    public static final String KEY_LANGUAGE = "LANGUAGE";
    public static final String KEY_APP_SETTING = "KEY_APP_SETTING";
    public static int KEY_LANGUAGE_MODE = 1;

    private Context mContext;
    private SharedPreferences preferences = null;

    public SharedPresUtils(Application application) {
        preferences = PreferenceManager.getDefaultSharedPreferences(application);
    }

    public SharedPresUtils(Context context) {
        this.mContext = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public int getSettingInt(String key) {
        int value = 0;
        if (preferences.contains(key))
            value = preferences.getInt(key, 0);
        return value;
    }

    public static void saveSetting(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveSetting(Context context, String key, Object o) {
        saveSetting(context, key, new Gson().toJson(o));
    }

    public void changeAppLanguage() {
        try {
            saveSetting(KEY_LANGUAGE, KEY_LANGUAGE_MODE);
            String keyText = (SharedPresUtils.KEY_LANGUAGE_MODE == 0) ? "vi" : "en";
            Resources res = this.mContext.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale(keyText.toLowerCase());
            res.updateConfiguration(conf, dm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSettingString(String key) {

        String value = null;

        if (preferences != null && preferences.contains(key))
            value = preferences.getString(key, "");

        return value;
    }

    public long getSettingLong(String key) {

        long value = 0L;

        if (preferences != null && preferences.contains(key))
            value = preferences.getLong(key, 0L);

        return value;
    }

    public FlightBooking getSetting(String key) {

        Gson gson = new Gson();
        String json = getSettingString(key);
        FlightBooking myObject = gson.fromJson(json, FlightBooking.class);
        return myObject;
    }

    public String onGetAppSetting() {
        return getSettingString(KEY_APP_SETTING);
    }

    public boolean saveSetting(String key, int value) {
        if (preferences == null) return false;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
        return true;
    }

    public boolean saveSetting(String key, long value) {
        if (preferences == null) return false;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
        return true;
    }

    public boolean saveSetting(String key, String value) {

        if (preferences == null) return false;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
        return true;
    }

    public boolean removeSettings(String... keys) {
        if (preferences == null) return false;

        SharedPreferences.Editor editor = preferences.edit();
        for (String key : keys) {
            editor.remove(key);
        }
        editor.apply();

        return true;
    }

    public boolean onApplyAppSetting(String json) {
        return saveSetting(KEY_APP_SETTING, json);
    }

}
