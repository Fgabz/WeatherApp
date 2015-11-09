package com.example.fanilo.weatherapp.api;

import com.example.fanilo.weatherapp.data.ForecastResponse;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fanilo on 9/7/15.
 */
public class OpenWeatherApi {

    private IOpenWeatherMapApi openWeatherMapApi;

    @Inject
    public OpenWeatherApi(IOpenWeatherMapApi openWeatherMapApi) {
        this.openWeatherMapApi = openWeatherMapApi;
    }

    public rx.Observable<ForecastResponse> getForecast(String place, String unit, int count, String language, String apiKey) {
        return openWeatherMapApi.getForecast(place, unit, count, language, apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}
