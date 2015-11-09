package com.example.fanilo.weatherapp.ui.Search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.fanilo.weatherapp.R;
import com.example.fanilo.weatherapp.WeatherApp;
import com.example.fanilo.weatherapp.ui.base.BaseActivity;
import com.example.fanilo.weatherapp.utils.MonitoringUtils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;
import timber.log.Timber;

public class SearchActivity extends BaseActivity implements SearchAdapter.OnClickListner{

    @Inject Hashtable<String, String> cityMap;
    @Inject MonitoringUtils monitoringUtils;

    @Bind(R.id.search_input_text) EditText searchInpuText;
    @Bind(R.id.suggestions_list) RecyclerView recyclerView;

    private List<String> suggestionList = new ArrayList<>();
    private SearchAdapter adapter;
    private BehaviorSubject<String> citySubject = BehaviorSubject.create();
    private BehaviorSubject<String> currentTermSubject = BehaviorSubject.create();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        WeatherApp.get(this)
                .getAppComponent()
                .inject(this);
        ButterKnife.bind(this);

        adapter = new SearchAdapter(getLayoutInflater());
        adapter.setOnClickListener(this);
        listenEditText();
    }

    private void searchInJson() {
        citySubject.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                suggestionList.add(s);
                adapter.addData(suggestionList);
                recyclerView.setAdapter(adapter);

                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Timber.e(throwable, "issue while adding city in behaviour subject");
            }
        });

    }

    private void filterList() {
        currentTermSubject.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                for (int i = 0; i < suggestionList.size(); i++) {
                    if (!suggestionList.get(i).toLowerCase().contains(s)) {
                        adapter.remove(i);
                    }
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Timber.e(throwable, "issue while filtering list");
            }
        });
    }


    private void listenEditText() {
        monitoringUtils.checkMemoryUsage();
        monitoringUtils.checkCPU();

        WidgetObservable.text(searchInpuText).map(new Func1<OnTextChangeEvent, String>() {
            @Override
            public String call(OnTextChangeEvent onTextChangeEvent) {
                return onTextChangeEvent.text().toString();
            }}).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String text) {
                if (text.isEmpty()) {
                    adapter.clear();
                }
                return text.length() >= 4;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String text) {
                suggestionList.clear();
                adapter.notifyDataSetChanged();
                for (Map.Entry<String, String> entry : cityMap.entrySet()) {
                    if (entry.getValue().toLowerCase().contains(text.toLowerCase())) {
                        citySubject.onNext(entry.getValue());
                        currentTermSubject.onNext(text.toLowerCase());
                    }
                }


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Timber.e(throwable, "issue while populating");
            }
        });

        searchInJson();
        filterList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
