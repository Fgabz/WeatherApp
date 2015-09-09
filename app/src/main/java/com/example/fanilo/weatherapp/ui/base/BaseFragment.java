package com.example.fanilo.weatherapp.ui.base;

import android.support.v4.app.Fragment;

import butterknife.ButterKnife;

/**
 * Created by fanilo on 9/7/15.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onDestroyView() {
        // sets ui views to null (also takes care of cleaning up possible leaks when using setRetainedInstance)
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
