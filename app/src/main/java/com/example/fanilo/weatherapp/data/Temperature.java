package com.example.fanilo.weatherapp.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fanilo on 9/7/15.
 */
public class Temperature implements Serializable{

    @SerializedName("day")
    private float mAverageTemperature;

    @SerializedName("min")
    private float mMinimumTemperature;

    @SerializedName("max")
    private float mMaximumTemperature;

    @SerializedName("night")
    private float mNightTemperature;

    @SerializedName("eve")
    private float mEveningTemperature;

    @SerializedName("morn")
    private float mMorningTemperature;


    public float getmAverageTemperature() {
        return mAverageTemperature;
    }

    public float getmMinimumTemperature() {
        return mMinimumTemperature;
    }

    public float getmMaximumTemperature() {
        return mMaximumTemperature;
    }

    public float getmNightTemperature() {
        return mNightTemperature;
    }

    public float getmEveningTemperature() {
        return mEveningTemperature;
    }

    public float getmMorningTemperature() {
        return mMorningTemperature;
    }
}
