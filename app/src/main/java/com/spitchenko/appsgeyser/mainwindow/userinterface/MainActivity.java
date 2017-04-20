package com.spitchenko.appsgeyser.mainwindow.userinterface;

import android.os.Bundle;

import com.spitchenko.appsgeyser.base.userinterface.BaseActivity;
import com.spitchenko.appsgeyser.mainwindow.controller.MainActivityController;

public final class MainActivity extends BaseActivity {
    MainActivityController controller = new MainActivityController(this);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        addObserver(controller);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        addObserver(controller);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        removeObserver(controller);
    }
}
