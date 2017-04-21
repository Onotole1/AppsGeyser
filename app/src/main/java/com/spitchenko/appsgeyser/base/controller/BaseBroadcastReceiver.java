package com.spitchenko.appsgeyser.base.controller;

import android.content.BroadcastReceiver;

import java.util.ArrayList;

import lombok.NonNull;

/**
 * Date: 21.04.17
 * Time: 8:53
 *
 * @author anatoliy
 *
 * Базовый broadcastReceiver содержит операции над списком наблюдателей
 * для оповещения их о новых событиях и передачи сообщений
 */
public abstract class BaseBroadcastReceiver extends BroadcastReceiver {
    private final static int NUMBER_OBSERVERS = 1;

    protected final ArrayList<BaseActivityController> observers = new ArrayList<>(NUMBER_OBSERVERS);

    public void addObserver(@NonNull final BaseActivityController observer) {
        observers.add(observer);
    }

    public void removeObserver(@NonNull final BaseActivityController observer) {
        final int index = observers.indexOf(observer);
        if (index >= 0) {
            observers.remove(index);
        }
    }

}
