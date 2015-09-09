package com.example.fanilo.weatherapp.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fanilo.weatherapp.R;
import com.example.fanilo.weatherapp.data.Forecast;
import com.example.fanilo.weatherapp.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fanilo on 9/7/15.
 */
public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.WeatherViewholder> {

    private OnClickListener onClickListener;
    private List<Forecast> forecastResponses = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private StringUtils stringUtils;


    public WeatherListAdapter(StringUtils stringUtils, LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        this.stringUtils = stringUtils;
    }

    public void setOnClickListener(final OnClickListener itemClickListener) {
        this.onClickListener = itemClickListener;
    }

    @Override
    public WeatherViewholder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view =
                layoutInflater.inflate(R.layout.weather_item, viewGroup, false);

        return new WeatherViewholder(view);
    }

    @Override
    public void onBindViewHolder(WeatherViewholder viewholder, int i) {
        Forecast forecast = forecastResponses.get(i);

        viewholder.currentDay.setText(stringUtils.timeFormat(forecast.getmTime()));
        viewholder.description.setText(forecast.getmWeather().get(0).getmWeatherDescription());
        viewholder.maxTemperature.setText(stringUtils.temperatureFormat(forecast.getmTemperature().getmMaximumTemperature()));
        viewholder.minTemperature.setText(stringUtils.temperatureFormat(forecast.getmTemperature().getmMinimumTemperature()));
    }

    @Override
    public int getItemCount() {
        return forecastResponses.size();
    }

    public interface OnClickListener {
        void onItemClick(View view, int position);
    }

    public void addData(List<Forecast> data) {
        forecastResponses.clear();
        if (data != null) {
            forecastResponses.addAll(data);
        }
        notifyDataSetChanged();
    }


    public class WeatherViewholder extends RecyclerView.ViewHolder {
        public WeatherViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(onItemClickListener);
        }

        @Bind(R.id.day) TextView currentDay;
        @Bind(R.id.description) TextView description;
        @Bind(R.id.max_temperature) TextView maxTemperature;
        @Bind(R.id.min_temperature) TextView minTemperature;


        final View.OnClickListener onItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view != null) {
                    onClickListener.onItemClick(view, getLayoutPosition());
                }
            }
        };
    }
}
