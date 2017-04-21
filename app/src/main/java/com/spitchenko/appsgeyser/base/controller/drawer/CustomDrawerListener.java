package com.spitchenko.appsgeyser.base.controller;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Date: 21.04.17
 * Time: 23:03
 *
 * @author anatoliy
 */
public class CustomDrawerListener implements DrawerLayout.DrawerListener {
    private final EditText editText;

    public CustomDrawerListener(final EditText editText) {
        this.editText = editText;
    }

    @Override
    public void onDrawerSlide(final View drawerView, final float slideOffset) {
        final AppCompatActivity activity = (AppCompatActivity) drawerView.getContext();

        final InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);

        final View focus = activity.getCurrentFocus();
        if (null != focus) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    0
            );

            editText.setFocusable(false);
            editText.setFocusableInTouchMode(true);
        }
    }

    @Override
    public void onDrawerOpened(final View drawerView) {
    }

    @Override
    public void onDrawerClosed(final View drawerView) {
    }

    @Override
    public void onDrawerStateChanged(final int newState) {
    }
}
