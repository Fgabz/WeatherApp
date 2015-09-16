package com.example.fanilo.weatherapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.fanilo.weatherapp.R;
import com.example.fanilo.weatherapp.WeatherApp;
import com.example.fanilo.weatherapp.api.OpenWeatherApi;
import com.example.fanilo.weatherapp.data.City;
import com.example.fanilo.weatherapp.data.ForecastResponse;
import com.example.fanilo.weatherapp.ui.Search.SearchActivity;
import com.example.fanilo.weatherapp.ui.base.BaseActivity;
import com.google.gson.Gson;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    private static final String PLACE = "Paris";
    private static final String LANG = "en";
    private static final String METRIC = "metric";
    private static final String JSON_FILE = "city.json";
    private static final int DAYS = 5;

    @Inject OpenWeatherApi openWeatherApi;
    @Inject Hashtable<String, String> cityMap;

    @Bind(R.id.main_view) CoordinatorLayout rootView;
    @Bind(R.id.error_text) TextView errorText;
    @Bind(R.id.progress) ProgressWheel progressWheel;
    @Bind(R.id.toolbar) Toolbar toolbar;

    private ForecastResponse forecastResponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WeatherApp.get(this)
                .getAppComponent()
                .inject(this);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            apiCall();
            populateHashTable();
        }
        else {
            progressWheel.setVisibility(View.GONE);
        }
    }

    private void populateHashTable() {
        String json;
        try {

            InputStream is = getAssets().open(JSON_FILE);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
            List<City> cityList= new
                    ArrayList<City>(Arrays.asList(new Gson().fromJson(json, City[].class)));

            for (int i = 0; i < cityList.size(); i++) {
                cityMap.put(cityList.get(i).getCityId(), cityList.get(i).getCityName());
            }



        } catch (IOException ex) {
            Timber.e(ex, "issue reading JSON file");
        }

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_search:
                startActivity(new Intent(this, SearchActivity.class));
                return true;
        }
        return false;
    }

    private void apiCall() {
        bind(openWeatherApi.getForecast(PLACE, METRIC, DAYS, LANG).subscribe(
                new Action1<ForecastResponse>() {
                    @Override
                    public void call(ForecastResponse forecastResponse) {
                        progressWheel.setVisibility(View.GONE);
                        forecastResponses = forecastResponse;
                        errorText.setVisibility(View.GONE);
                        WeatherListFragment weatherListFragment = WeatherListFragment
                                .newInstance(forecastResponses);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, weatherListFragment).commit();
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Timber.e(throwable, "issue getting forecast from API");
                        errorText.setVisibility(View.VISIBLE);
                        progressWheel.setVisibility(View.VISIBLE);
                        Snackbar.make(rootView, getString(R.string.error_message_snackbar),
                                Snackbar.LENGTH_INDEFINITE)
                                .setAction(getString(R.string.retry), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        apiCall();
                                    }
                        }).show();
                    }
                }));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
}
