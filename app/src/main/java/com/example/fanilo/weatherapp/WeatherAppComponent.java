package com.example.fanilo.weatherapp;

import com.example.fanilo.weatherapp.annotation.PerApp;
import com.example.fanilo.weatherapp.api.ApiModule;
import com.example.fanilo.weatherapp.ui.detail.WeatherDetailFragment;
import com.example.fanilo.weatherapp.ui.home.MainActivity;
import com.example.fanilo.weatherapp.ui.home.WeatherListFragment;
import com.example.fanilo.weatherapp.utils.UtilsModule;

import dagger.Component;

/**
 * Created by fanilo on 9/7/15.
 */

@PerApp
@Component(
        modules = {
                WeatherAppModule.class,
                ApiModule.class,
                UtilsModule.class,
        }
)
public interface WeatherAppComponent {

        void inject(MainActivity mainActivity);

        void inject(WeatherDetailFragment weatherDetailFragment);

        void inject(WeatherListFragment weatherListFragment);
}
