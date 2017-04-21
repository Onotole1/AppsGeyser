package com.spitchenko.appsgeyser.mainwindow.userinterface;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.spitchenko.appsgeyser.base.userinterface.BaseActivity;
import com.spitchenko.appsgeyser.mainwindow.controller.MainActivityController;

/**
 * Date: 20.04.17
 * Time: 15:32
 *
 * @author anatoliy
 *
 * Активность главного экрана. Имеет android:launchMode= "singleInstance" в манифесте.
 * Содержит действия над подписчиками.
 */
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

    /**
     * Интерфейс по запуску активности
     * @param context - контекст
     */
    public static void start(final Context context) {
        final Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
