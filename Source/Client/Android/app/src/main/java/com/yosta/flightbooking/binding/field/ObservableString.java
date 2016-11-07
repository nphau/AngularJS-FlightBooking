package com.yosta.flightbooking.binding.field;

import android.databinding.BaseObservable;
import android.databinding.BindingConversion;

public class ObservableString extends BaseObservable {

    private String value = "";

    public ObservableString(String value) {
        this.value = value;
    }

    public ObservableString() {
    }

    public String get() {
        return value != null ? value : "";
    }

    public void set(String value) {
        if (value == null) value = "";
        if (!this.value.contentEquals(value)) {
            this.value = value;
            notifyChange();
        }
    }

    public void set(int value) {
        String strValue = String.valueOf(value);
        set(strValue);
    }

    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }

    public void clear() {
        set(null);
    }

    @BindingConversion
    public static String convertToString(
            ObservableString observableString) {
        return observableString.get();
    }
}

