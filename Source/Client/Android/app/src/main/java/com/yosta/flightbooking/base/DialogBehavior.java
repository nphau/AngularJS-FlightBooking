package com.yosta.flightbooking.base;

import android.content.Context;

/**
 * Created by Phuc-Hau Nguyen on 9/8/2016.
 */
public interface DialogBehavior {

    void onAttachedWindow(Context context);

    void onApplyComponents();

    void onApplyData();

    void onApplyEvents();


}
