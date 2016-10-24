package com.yosta.flightbooking;

import android.content.Intent;
import android.os.Handler;

import com.yosta.flightbooking.base.ActivityBehavior;

import butterknife.ButterKnife;

public class SplashActivity extends ActivityBehavior {

    @Override
    public void onApplyComponents() {
        super.onApplyComponents();
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    public void onApplyEvents() {
        super.onApplyEvents();

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, FlightFindingActivity.class));
                finish();
            }
        };
        handler.postDelayed(runnable, 5000);
    }
}
