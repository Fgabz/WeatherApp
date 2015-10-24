package com.example.fanilo.weatherapp.api;

import com.example.fanilo.weatherapp.data.ForecastResponse;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by fanilo on 9/7/15.
 */
public interface IOpenWeatherMapApi {
    @GET("/forecast/daily")
    rx.Observable<ForecastResponse> getForecast(@Query("q") String place, @Query("units")
    String unit, @Query("cnt") int count, @Query("lang") String language, @Query("APPID") String apiKey);
}
