package com.example.fanilo.weatherapp.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.net.TrafficStats;
import com.yozio.android.Yozio;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fanilo on 9/16/15.
 */
public class MonitoringUtils {

    private Context context;
    private static final long CONVERT_MEGABYTE = 1048576L;

    @Inject
    public MonitoringUtils(Context context) {
        this.context = context;
    }

    public void checkMemoryUsage() {
        Observable<Long> obs = Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                subscriber.onNext(memoryUsage());
                subscriber.onCompleted();

            }
        });

        obs.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<Long>() {
                @Override
                public void call(Long value) {
                    Yozio.trackCustomEvent(context, "memoryusage", value);
                    Timber.d("Memory Usage, percentage used  %s", String.valueOf(value));
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Timber.e(throwable, "error getting memeory usage");
                }
            });
    }

    private long memoryUsage() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        Timber.d("Current thread call MEMORY CHECK " + Thread.currentThread().getName());

        //Convert in Megabyte
        long availableMegs = mi.availMem / CONVERT_MEGABYTE;
        long totalMegs = mi.totalMem / CONVERT_MEGABYTE;

        long percentUsed = 100L - availableMegs * 100 / totalMegs;

        Timber.d("Memory Usage, memoryInfo.availMem  %s", String.valueOf(availableMegs));

        return percentUsed;
    }

    private long networkUsage() {
        long mStartRX;

        Timber.d("Current thread call NETWORK CHECK" + Thread.currentThread().getName());

        mStartRX = TrafficStats.getTotalRxBytes();

        if (mStartRX == TrafficStats.UNSUPPORTED) {
            Timber.e("Issue getting traffic usage");
        }

        return mStartRX;
    }


    public void checkNetworkUsage() {
        Observable.just(networkUsage()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long value) {
                long rxBytes = TrafficStats.getTotalRxBytes()- value;
                Yozio.trackCustomEvent(context, "networkusage", rxBytes);
                Timber.d("NetWork Usage, bytes received  %s", String.valueOf(rxBytes));
            }
        });
    }

    public void checkCPU() {
        Observable<String> obs = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(readUsage());
                subscriber.onCompleted();
            }
        });


        obs.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(new Func1<String, Integer>() {
                @Override
                public Integer call(String s) {
                    Timber.d("Current thread call MAP " + Thread.currentThread().getName());

                    String[] split = s.split(" ");
                    String userCPU = split[1].replaceAll("[^0-9]", "");
                    String systemCPU = split[3].replaceAll("[^0-9]", "");
                    return Integer.parseInt(userCPU) + Integer.parseInt(systemCPU);
                }
            })
            .subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer value) {
                    Yozio.trackCustomEvent(context, "checkcpu", value);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Timber.e(throwable, "error checking CPU");
                }
            });
    }

    private String readUsage() {
        Timber.d("Current thread call CPU CHECK" + Thread.currentThread().getName());

        String response = "";
        try {
            Process process = Runtime.getRuntime().exec("top -n -1");
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = in.readLine()) != null) {
                if (line.contains("User")) {
                    response = line;
                    Timber.d("CPU USAGE " + response);
                    break;
                }
            }
        } catch (IOException e) {
            Timber.e(e, "issue exec command top");
        }

        return response;
    }
}
