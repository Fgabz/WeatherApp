package com.example.fanilo.weatherapp;

import android.app.Application;
import android.content.Context;

import com.example.fanilo.weatherapp.api.ApiModule;
import com.example.fanilo.weatherapp.utils.UtilsModule;

import timber.log.Timber;

/**
 * Created by fanilo on 9/7/15.
 */
public class WeatherApp extends Application {
    private WeatherAppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initiliazeInjector();
    }

    private void initiliazeInjector() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        this.appComponent = DaggerWeatherAppComponent.builder()
                .weatherAppModule(new WeatherAppModule(this))
                .apiModule(new ApiModule())
                .utilsModule(new UtilsModule())
                .build();
    }

    public WeatherAppComponent getAppComponent() {
        return this.appComponent;
    }

    public static WeatherApp get(Context context) {
        return (WeatherApp) context.getApplicationContext();
    }
}
