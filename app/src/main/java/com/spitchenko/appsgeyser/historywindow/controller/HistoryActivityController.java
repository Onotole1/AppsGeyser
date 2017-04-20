package com.spitchenko.appsgeyser.historywindow.controller;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.spitchenko.appsgeyser.R;
import com.spitchenko.appsgeyser.base.controller.BaseActivityController;
import com.spitchenko.appsgeyser.model.ResponseTrio;

import java.util.ArrayList;

import lombok.NonNull;

/**
 * Date: 20.04.17
 * Time: 22:46
 *
 * @author anatoliy
 */
public class HistoryActivityController implements BaseActivityController {
    private final AppCompatActivity activity;

    public HistoryActivityController(final AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void updateOnCreate(@Nullable final Bundle savedInstanceState) {
        activity.setContentView(R.layout.activity_history);
    }

    @Override
    public void updateOnSavedInstanceState(@NonNull final Bundle outState) {

    }

    @Override
    public void updateOnRestoreInstanceState(@NonNull final Bundle savedInstanceState) {

    }

    @Override
    public void updateOnResume() {

    }

    @Override
    public void updateOnPause() {

    }

    public void updateOnUpdate(final ArrayList<Parcelable> parcelables) {
        final ArrayList<ResponseTrio> responseTrios = new ArrayList<>();
        for (int i = 0, size = parcelables.size(); i < size; i++) {
            final Parcelable parcel = parcelables.get(i);
            if (parcel instanceof ResponseTrio) {
                responseTrios.add((ResponseTrio) parcel);
            }
        }


    }
}
