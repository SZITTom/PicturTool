package com.szittom.picturtool.app;

import android.app.Activity;
import android.os.Bundle;

import com.jude.beam.bijection.ActivityLifeCycleDelegate;
import com.szittom.picturtool.utils.L;

/**
 * Created by SZITTom on 2016/4/18.
 */
public class ActivityDelegate extends ActivityLifeCycleDelegate {

    private static final String TAG = ActivityDelegate.class.getSimpleName();

    public ActivityDelegate(Activity act) {
        super(act);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.i(TAG, "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.i(TAG, "onDestory");
    }
}