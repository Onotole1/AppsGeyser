package com.spitchenko.appsgeyser.historywindow.userinterface;

import android.os.Bundle;

import com.spitchenko.appsgeyser.base.userinterface.BaseActivity;
import com.spitchenko.appsgeyser.historywindow.controller.HistoryActivityController;

public class HistoryActivity extends BaseActivity {
    HistoryActivityController historyActivityController = new HistoryActivityController(this);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        addObserver(historyActivityController);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        addObserver(historyActivityController);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        removeObserver(historyActivityController);
    }
}
