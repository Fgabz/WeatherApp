package com.example.fanilo.weatherapp.ui.detail;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fanilo.weatherapp.R;
import com.example.fanilo.weatherapp.WeatherApp;
import com.example.fanilo.weatherapp.data.Forecast;
import com.example.fanilo.weatherapp.ui.base.BaseFragment;
import com.example.fanilo.weatherapp.ui.home.MainActivity;
import com.example.fanilo.weatherapp.utils.StringUtils;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class WeatherDetailFragment extends BaseFragment {
    private static final String ARG_DETAIL_FORECAST = "detail_forecast";

    private static final String URL_ICON = "http://openweathermap.org/img/w/";

    @Inject StringUtils stringUtils;
    @Inject Picasso picasso;

    @Bind(R.id.detail_day) TextView day;
    @Bind(R.id.detail_temperature) TextView temperature;
    @Bind(R.id.detail_description) TextView description;
    @Bind(R.id.humidity_value) TextView humidity;
    @Bind(R.id.pressure) TextView pressure;
    @Bind(R.id.cloud) TextView cloud;
    @Bind(R.id.winds) TextView winds;
    @Bind(R.id.morning) TextView morningTemperature;
    @Bind(R.id.day) TextView dayTemperature;
    @Bind(R.id.evening) TextView eveningTemperature;
    @Bind(R.id.night) TextView nightTemperature;
    @Bind(R.id.icon_weather) ImageView icon;

    private Forecast forecast;

    public static WeatherDetailFragment newInstance(Forecast forecast) {
        WeatherDetailFragment fragment = new WeatherDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_DETAIL_FORECAST, Parcels.wrap(forecast));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            forecast = Parcels.unwrap(getArguments().getParcelable(ARG_DETAIL_FORECAST));
        }

        WeatherApp.get(getContext())
                .getAppComponent()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void setUpToolBar() {
        MainActivity activity = (MainActivity) getActivity();

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        if (upArrow != null) {
            upArrow.setColorFilter(getResources().getColor(R.color.white),
                    PorterDuff.Mode.SRC_ATOP);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpToolBar();

        picasso.load(URL_ICON + forecast.getmWeather().get(0).getmIcon() + ".png")
                .into(icon);

        day.setText(stringUtils.timeFormat(forecast.getmTime()));
        temperature.setText(stringUtils.temperatureFormat(forecast.getmTemperature().getmAverageTemperature()));
        description.setText(forecast.getmWeather().get(0).getmWeatherDescription());
        humidity.setText(stringUtils.percentageFormat(forecast.getmHumidity()));
        pressure.setText(stringUtils.percentageFormat(forecast.getmPressure()));
        cloud.setText(stringUtils.percentageFormat(forecast.getmCloudiness()));
        winds.setText(stringUtils.percentageFormat(forecast.getmWindSpeed()));
        morningTemperature.setText(stringUtils.temperatureFormat(forecast.getmTemperature().getmMorningTemperature()));
        eveningTemperature.setText(stringUtils.temperatureFormat(forecast.getmTemperature().getmEveningTemperature()));
        dayTemperature.setText(stringUtils.temperatureFormat(forecast.getmTemperature().getmAverageTemperature()));
        nightTemperature.setText(stringUtils.temperatureFormat(forecast.getmTemperature().getmNightTemperature()));
    }
}
