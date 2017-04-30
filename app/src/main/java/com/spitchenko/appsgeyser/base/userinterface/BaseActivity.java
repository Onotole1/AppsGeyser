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
 *
 * Базовая активность содержит операции со списком наблюдателей.
 * Такой подход позволяет декомпозировать активность на контроллер и представление
 */
public class BaseActivity extends AppCompatActivity {
    private final static int NUMBER_OBSERVERS = 1;
    private final ArrayList<BaseActivityController> observers = new ArrayList<>(NUMBER_OBSERVERS);
    private final BaseActivityController baseActivityController = new BaseActivityController(this);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        addObserver(baseActivityController);
        super.onCreate(savedInstanceState);
        notifyObserversOnCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        addObserver(baseActivityController);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            removeObserver(baseActivityController);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        removeObserver(baseActivityController);
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

    private void notifyObserversOnSetMainFragment() {
        for (int i = 0, size = observers.size(); i < size; i++) {
            final BaseActivityController observer = observers.get(i);
            observer.updateOnSetMainFragment();
        }
    }

    private void notifyObserversOnSetHistoryFragment() {
        for (int i = 0, size = observers.size(); i < size; i++) {
            final BaseActivityController observer = observers.get(i);
            observer.updateOnHistoryFragment();
        }
    }

    public void setMainFragment() {
        notifyObserversOnSetMainFragment();
    }

    public void setHistoryFragment() {
        notifyObserversOnSetHistoryFragment();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
