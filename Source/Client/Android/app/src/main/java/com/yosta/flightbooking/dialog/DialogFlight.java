package com.yosta.flightbooking.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.yosta.flightbooking.R;
import com.yosta.flightbooking.binding.viewmodel.FlightBookingVM;
import com.yosta.flightbooking.databinding.ViewDialogFlightBinding;
import com.yosta.flightbooking.helper.utils.SharedPresUtils;
import com.yosta.flightbooking.model.flight.FlightBooking;

/**
 * Created by Phuc-Hau Nguyen on 8/31/2016.
 */
public class DialogFlight extends Dialog {

    private FlightBooking flightBooking = null;
    private SharedPresUtils sharedPresUtils = null;

    public DialogFlight(Context context) {
        super(context, R.style.AppTheme_CustomDialog);
        sharedPresUtils = new SharedPresUtils(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_dialog_flight);

        ViewDialogFlightBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()), R.layout.
                        view_dialog_flight, null, false);

        setContentView(binding.getRoot());

        flightBooking = sharedPresUtils.getSetting(SharedPresUtils.KEY_BOOKING_FLIGHT);
        binding.setFlightbooking(new FlightBookingVM(flightBooking));
    }
}