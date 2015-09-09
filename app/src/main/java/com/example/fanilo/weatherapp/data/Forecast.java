package com.example.fanilo.weatherapp.data;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by fanilo on 9/8/15.
 */
@Parcel
public class Forecast  {

    public Forecast() {
    }

    @SerializedName("dt")
    long mTime;

    @SerializedName("pressure")
    float mPressure;

    @SerializedName("humidity")
    int mHumidity;

    @SerializedName("temp")
    Temperature mTemperature;

    @SerializedName("weather")
    List<Weather> mWeather;

    @SerializedName("speed")
    float mWindSpeed;

    @SerializedName("deg")
    int mWindDirection;

    @SerializedName("clouds")
    int mCloudiness;


    public long getmTime() {
        return mTime;
    }

    public float getmPressure() {
        return mPressure;
    }

    public int getmHumidity() { return mHumidity; }

    public Temperature getmTemperature() {
        return mTemperature;
    }

    public List<Weather> getmWeather() {
        return mWeather;
    }

    public float getmWindSpeed() {
        return mWindSpeed;
    }

    public int getmWindDirection() {
        return mWindDirection;
    }

    public int getmCloudiness() {
        return mCloudiness;
    }
}
