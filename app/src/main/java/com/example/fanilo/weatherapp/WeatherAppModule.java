package com.example.fanilo.weatherapp;

import android.app.Application;
import android.content.Context;

import com.example.fanilo.weatherapp.annotation.PerApp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fanilo on 9/7/15.
 */

@Module
public class WeatherAppModule {
    private WeatherApp appApplication;

    public WeatherAppModule(WeatherApp appApplication) {
        this.appApplication = appApplication;
    }

    @Provides @PerApp
    WeatherApp provideApp() {
        return this.appApplication;
    }

    @Provides @PerApp
    Application provideApplication(WeatherApp app) {return app;}

    @Provides @PerApp
    Context provideAppContext() {return this.appApplication;}
}
