package com.spitchenko.appsgeyser.mainwindow.controller;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.spitchenko.appsgeyser.R;
import com.spitchenko.appsgeyser.base.controller.BaseActivityController;

import lombok.NonNull;

/**
 * Date: 20.04.17
 * Time: 15:32
 *
 * @author anatoliy
 */
public final class MainActivityController implements BaseActivityController {
    private final AppCompatActivity activity;
    private LocalBroadcastManager localBroadcastManager;
    private final MainActivityBroadcastReceiver mainActivityBroadcastReceiver
            = new MainActivityBroadcastReceiver();

    public MainActivityController(final AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public final void updateOnCreate(@Nullable final Bundle savedInstanceState) {
        activity.setContentView(R.layout.activity_main);

        final EditText editText = (EditText) activity.findViewById(R.id.activity_main_editText);
        final FloatingActionButton fab = (FloatingActionButton) activity
                .findViewById(R.id.activity_main_floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String inputText = editText.getText().toString();
                if (!inputText.isEmpty()) {
                    WordsIntentService.start(WordsIntentService.getLanguageDetectKey(), activity
                            , inputText);
                } else {
                    Toast.makeText(activity, "Введите текст", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public final void updateOnResume() {
        localBroadcastManager = LocalBroadcastManager.getInstance(activity);
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MainActivityBroadcastReceiver.getReceiveActionKey());
        localBroadcastManager.registerReceiver(mainActivityBroadcastReceiver, intentFilter);

        mainActivityBroadcastReceiver.addObserver(this);
    }

    @Override
    public final void updateOnPause() {
        if (null != localBroadcastManager) {
            localBroadcastManager.unregisterReceiver(mainActivityBroadcastReceiver);
        }
        mainActivityBroadcastReceiver.removeObserver(this);
    }

    void updateOnUpdate(final String language) {
        Toast.makeText(activity, "Язык текста " + language, Toast.LENGTH_SHORT).show();
    }

    @Override
    public final void updateOnSavedInstanceState(@NonNull final Bundle outState) {

    }

    @Override
    public final void updateOnRestoreInstanceState(@NonNull final Bundle savedInstanceState) {

    }
}
