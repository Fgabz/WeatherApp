package com.example.fanilo.weatherapp.data;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by fanilo on 9/7/15.
 */

@Parcel
public class ForecastResponse {
    public ForecastResponse(){ /*Required empty bean constructor*/ }

    @SerializedName("list")
    List<Forecast> mForecasts;

    public List<Forecast> getForecasts() {
        return mForecasts;
    }
}
