package com.example.fanilo.weatherapp.ui.home;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fanilo.weatherapp.R;
import com.example.fanilo.weatherapp.WeatherApp;
import com.example.fanilo.weatherapp.data.ForecastResponse;
import com.example.fanilo.weatherapp.ui.base.BaseFragment;
import com.example.fanilo.weatherapp.ui.detail.WeatherDetailFragment;
import com.example.fanilo.weatherapp.utils.StringUtils;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class WeatherListFragment extends BaseFragment implements WeatherListAdapter.OnClickListener {
    private static final String ARG_FORCAST = "forcast-list";

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.temparture)
    TextView temperature;

    @Inject
    StringUtils stringUtils;

    private ForecastResponse forecastResponse;
    private WeatherListAdapter adapter;
    private ActionBar actionBar;


    public static WeatherListFragment newInstance(ForecastResponse forecastResponse) {
        WeatherListFragment fragment = new WeatherListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FORCAST, Parcels.wrap(forecastResponse));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            forecastResponse = Parcels.unwrap(getArguments().getParcelable(ARG_FORCAST));
        }
        WeatherApp.get(getContext())
                .getAppComponent()
                .inject(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        setUpToolBar();
    }

    private void setUpToolBar() {
        MainActivity activity = (MainActivity) getActivity();

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);

        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(R.string.toolbar_title);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        temperature.setText(stringUtils.temperatureFormat(forecastResponse.getForecasts().get(0)
                .getmTemperature().getmAverageTemperature()));

        adapter = new WeatherListAdapter(stringUtils, getActivity().getLayoutInflater());
        adapter.addData(forecastResponse.getForecasts());
        adapter.setOnClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    @Override
    public void onItemClick(View view, int position) {
        WeatherDetailFragment weatherDetailFragment = WeatherDetailFragment
                .newInstance(forecastResponse.getForecasts().get(position));
        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, weatherDetailFragment)
                .commit();
    }
}
