package com.yosta.flightbooking.dagger.component;

import com.yosta.flightbooking.BookActivity;
import com.yosta.flightbooking.FlightFindingActivity;
import com.yosta.flightbooking.dagger.module.AppModule;
import com.yosta.flightbooking.dagger.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Phuc-Hau Nguyen on 10/27/2016.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {

    void inject(FlightFindingActivity activity);

    void inject(BookActivity activity);
}