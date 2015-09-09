package com.example.fanilo.weatherapp.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by fanilo on 9/8/15.
 */
public class BaseActivity extends AppCompatActivity {
  private final CompositeSubscription compSub = new CompositeSubscription();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onDestroy() {
    compSub.unsubscribe();
    ButterKnife.unbind(this);
    super.onDestroy();
  }

  protected Subscription bind(Subscription subscription) {
    compSub.add(subscription);
    return subscription;
  }
}
