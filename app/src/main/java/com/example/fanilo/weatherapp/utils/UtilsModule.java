package com.example.fanilo.weatherapp.utils;

import android.content.Context;

import com.example.fanilo.weatherapp.annotation.PerApp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fanilo on 9/8/15.
 */
@Module
public class UtilsModule {

    @PerApp
    @Provides
    StringUtils provideStringUtils(Context context) {
        return new StringUtils(context);
    }
}
