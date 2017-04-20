package com.spitchenko.appsgeyser.mainwindow.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;

import lombok.NonNull;

/**
 * Date: 20.04.17
 * Time: 21:46
 *
 * @author anatoliy
 */
public class MainActivityBroadcastReceiver extends BroadcastReceiver {
    private final static String MAIN_ACTIVITY_BROADCAST_RECEIVER
            = "com.spitchenko.appsgeyser.mainwindow.controller.MainActivityBroadcastReceiver";
    private final static String RECEIVE_ACTION = MAIN_ACTIVITY_BROADCAST_RECEIVER + ".receive";
    private final static int NUMBER_OBSERVERS = 1;

    private final ArrayList<MainActivityController> observers = new ArrayList<>(NUMBER_OBSERVERS);

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (intent.getAction().equals(RECEIVE_ACTION)) {
            notifyObserversUpdate(intent.getStringExtra(RECEIVE_ACTION));
        }
    }

    public void addObserver(@NonNull final MainActivityController observer) {
        observers.add(observer);
    }

    public void removeObserver(@NonNull final MainActivityController observer) {
        final int index = observers.indexOf(observer);
        if (index >= 0) {
            observers.remove(index);
        }
    }


    public void notifyObserversUpdate(final String language) {
        for (int i = 0, size = observers.size(); i < size; i++) {
            final MainActivityController observer = observers.get(i);
            observer.updateOnUpdate(language);
        }
    }

    public static String getReceiveActionKey() {
        return RECEIVE_ACTION;
    }

    public static void sendToBroadcast(@NonNull final String language
            , @NonNull final String action, @NonNull final String packageName
            , @NonNull final Context context) {
        final Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(action);
        broadcastIntent.setPackage(packageName);
        broadcastIntent.putExtra(action, language);
        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
    }
}
