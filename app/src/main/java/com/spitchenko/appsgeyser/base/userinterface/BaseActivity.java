package com.spitchenko.appsgeyser.base.userinterface;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.spitchenko.appsgeyser.base.controller.BaseActivityController;

import java.util.ArrayList;

/**
 * Date: 20.04.17
 * Time: 16:06
 *
 * @author anatoliy
 */
public class BaseActivity extends AppCompatActivity {
    private final static int NUMBER_OBSERVERS = 1;
    private final ArrayList<BaseActivityController> observers = new ArrayList<>(NUMBER_OBSERVERS);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notifyObserversOnCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifyObserversOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        notifyObserversOnPause();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        notifyObserversOnSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        notifyObserversOnRestoreInstanceState(savedInstanceState);
    }

    protected void addObserver(final BaseActivityController observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    protected void removeObserver(final BaseActivityController observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    private void notifyObserversOnCreate(final Bundle savedInstanceState) {
        for (int i = 0, size = observers.size(); i < size; i++) {
            final BaseActivityController observer = observers.get(i);
            observer.updateOnCreate(savedInstanceState);
        }
    }

    private void notifyObserversOnResume() {
        for (int i = 0, size = observers.size(); i < size; i++) {
            final BaseActivityController observer = observers.get(i);
            observer.updateOnResume();
        }
    }

    private void notifyObserversOnPause() {
        for (int i = 0, size = observers.size(); i < size; i++) {
            final BaseActivityController observer = observers.get(i);
            observer.updateOnPause();
        }
    }

    private void notifyObserversOnSaveInstanceState(final Bundle outState) {
        for (int i = 0, size = observers.size(); i < size; i++) {
            final BaseActivityController observer = observers.get(i);
            observer.updateOnSavedInstanceState(outState);
        }
    }

    private void notifyObserversOnRestoreInstanceState(final Bundle savedInstanceState) {
        for (int i = 0, size = observers.size(); i < size; i++) {
            final BaseActivityController observer = observers.get(i);
            observer.updateOnRestoreInstanceState(savedInstanceState);
        }
    }
}
