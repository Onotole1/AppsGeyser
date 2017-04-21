package com.spitchenko.appsgeyser.historywindow.userinterface;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.spitchenko.appsgeyser.base.userinterface.BaseActivity;
import com.spitchenko.appsgeyser.historywindow.controller.HistoryActivityController;

/**
 * Date: 20.04.17
 * Time: 22:46
 *
 * @author anatoliy
 *
 * Активность экрана истории. Имеет android:launchMode= "singleInstance" в манифесте.
 * Содержит действия над подписчиками.
 */

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
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        removeObserver(historyActivityController);
    }

    /**
     * Интерфейс для запуска активности
     * @param context - контекст
     */
    public static void start(final Context context) {
        final Intent intent = new Intent(context, HistoryActivity.class);
        context.startActivity(intent);
    }
}
