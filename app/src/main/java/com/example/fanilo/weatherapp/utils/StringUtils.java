package com.example.fanilo.weatherapp.utils;

import android.content.Context;

import com.example.fanilo.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by fanilo on 9/8/15.
 */
public class StringUtils {

    private final static long MILLISECONDS_IN_SECONDS = 1000;
    private Context context;

    @Inject
    public StringUtils(Context context) {
        this.context = context;
    }


    public  String temperatureFormat(float temperature) {
        return String.valueOf(Math.round(temperature)) + "°";
    }

    public  String percentageFormat(float value) {
        return String.valueOf(value) + "%";
    }


    public String timeFormat(final long unixTimestamp) {
        final long milliseconds = unixTimestamp * MILLISECONDS_IN_SECONDS;
        String day;

        if (isToday(milliseconds)) {
            day = context.getResources().getString(R.string.today);
        } else if (isTomorrow(milliseconds)) {
            day = context.getResources().getString(R.string.tomorrow);
        } else {
            day = getDayOfWeek(milliseconds);
        }

        return day;
    }

    private String getDayOfWeek(final long milliseconds) {
        return new SimpleDateFormat("EEEE").format(new Date(milliseconds));
    }

    private boolean isToday(final long milliseconds) {
        final SimpleDateFormat dayInYearFormat = new SimpleDateFormat("yyyyD");
        final String nowHash = dayInYearFormat.format(new Date());
        final String comparisonHash = dayInYearFormat.format(new Date(milliseconds));
        return nowHash.equals(comparisonHash);
    }

    private boolean isTomorrow(final long milliseconds) {
        final SimpleDateFormat dayInYearFormat = new SimpleDateFormat("yyyyD");
        final int tomorrowHash = Integer.parseInt(dayInYearFormat.format(new Date())) + 1;
        final int comparisonHash = Integer.parseInt(dayInYearFormat.format(new Date(milliseconds)));
        return comparisonHash == tomorrowHash;
    }
}
