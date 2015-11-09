package com.example.fanilo.weatherapp.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fanilo on 9/16/15.
 */
public class City {

    @SerializedName("name")
    private String cityName;

    @SerializedName("country")
    private String country;

    @SerializedName("_id")
    private String cityId;

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    public String getCityId() {
        return cityId;
    }


}
