package com.example.fanilo.weatherapp.api;

import android.app.Application;
import android.net.Uri;

import com.example.fanilo.weatherapp.BuildConfig;
import com.example.fanilo.weatherapp.annotation.PerApp;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Hashtable;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import timber.log.Timber;

/**
 * Created by fanilo on 9/7/15.
 */

@Module
public class ApiModule {

    private static final String API_URL = "http://api.openweathermap.org/data/2.5";
    private static final long DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    @Provides
    @PerApp OkHttpClient provideOkHttpClient(final Application app) {
        OkHttpClient client = new OkHttpClient();
        setUpCache(app, client);
        return client;
    }

    @Provides
    @PerApp
    Picasso providePicasso(Application app, OkHttpClient client) {
        return new Picasso.Builder(app).downloader(new OkHttpDownloader(client))
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Timber.e(exception, "Failed to load image: %s", uri);
                    }
                })
                .build();
    }

    public static void setUpCache(Application app, OkHttpClient client) {
        // Install an HTTP cache in the application cache directory.
        final File cacheDir = new File(app.getCacheDir(), "http");
        final Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        client.setCache(cache);
    }


    @Provides
    @PerApp
    Client provideRetrofitClient(OkHttpClient okHttpClient) {
        return new OkClient(okHttpClient);
    }

    @Provides
    @PerApp Endpoint provideEndpoint() {
        return Endpoints.newFixedEndpoint(API_URL);
    }

    @Provides
    @PerApp
    RestAdapter provideRestAdapter(Endpoint endpoint, Client client) {
        return new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setClient(client)
                .setConverter(new GsonConverter(new GsonBuilder().create()))
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build();
    }

    @Provides
    @PerApp IOpenWeatherMapApi provideOpenWeatherMaService(RestAdapter restAdapter) {
        return restAdapter.create(IOpenWeatherMapApi.class);
    }

    @Provides
    @PerApp OpenWeatherApi provideOpenWeatherApi(IOpenWeatherMapApi openWeatherMapApi) {
        return new OpenWeatherApi(openWeatherMapApi);
    }

    @Provides
    @PerApp
    Hashtable<String, String> provideCityMap() {
        return new Hashtable<>();
    }
}
