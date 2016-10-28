package com.yosta.flightbooking.config;

import android.app.Application;

import com.yosta.flightbooking.dagger.component.DaggerNetComponent;
import com.yosta.flightbooking.dagger.component.NetComponent;
import com.yosta.flightbooking.dagger.module.AppModule;
import com.yosta.flightbooking.dagger.module.NetModule;
import com.yosta.flightbooking.networking.IFlightBookingAPI;

/**
 * Created by Phuc-Hau Nguyen on 10/22/2016.
 */

public class AppConfig extends Application {

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(IFlightBookingAPI.BASE_URL))
                .build();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
