package com.yosta.flightbooking.binding.adapter;

import android.databinding.BindingAdapter;
import android.support.v4.util.Pair;
import android.widget.EditText;
import android.widget.TextView;

import com.yosta.flightbooking.R;
import com.yosta.flightbooking.binding.TextChangeListener;
import com.yosta.flightbooking.binding.field.ObservableString;

public final class BindingAdapters {

    private BindingAdapters() {
        throw new AssertionError();
    }
/*
    @SuppressWarnings("unchecked")
    @BindingAdapter("android:text")
    public static void bindEditText(EditText view,
                                    final ObservableString observableString) {

        Pair<ObservableString, TextChangeListener> pair =
                (Pair) view.getTag(R.id.bound_observable);

        if (pair == null || pair.first != observableString) {
            if (pair != null) view.removeTextChangedListener(pair.second);

            TextChangeListener watcher = new TextChangeListener(
                    (s, start, before, count) -> observableString.set(s.toString()));

            view.setTag(R.id.bound_observable, new Pair<>(observableString, watcher));
            view.addTextChangedListener(watcher);
        }
        String newValue = observableString.get();
        if (!view.getText().toString().equals(newValue))
            view.setText(newValue);
    }*/

    /*@BindingAdapter("android:text")
    public static void bindTextView(TextView view, String value) {
        view.setText(String.valueOf(value));
    }*/
}
