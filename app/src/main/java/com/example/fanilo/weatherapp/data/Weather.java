package com.example.fanilo.weatherapp.data;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by fanilo on 9/7/15.
 */

@Parcel
public class Weather {

    public Weather() {
    }

    @SerializedName("main")
    String mWeather;

    @SerializedName("description")
    String mWeatherDescription;

    @SerializedName("icon")
    String mIcon;

    public String getmWeather() {
        return mWeather;
    }

    public String getmWeatherDescription() {
        return mWeatherDescription;
    }

    public String getmIcon() {
        return mIcon;
    }
}
