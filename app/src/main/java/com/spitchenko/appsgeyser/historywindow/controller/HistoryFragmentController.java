package com.spitchenko.appsgeyser.historywindow.controller;

import android.app.Activity;
import android.app.Fragment;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.spitchenko.appsgeyser.R;
import com.spitchenko.appsgeyser.base.controller.BaseFragmentController;
import com.spitchenko.appsgeyser.model.ResponseTrio;

import java.util.ArrayList;
import java.util.Collections;

import lombok.NonNull;

/**
 * Date: 20.04.17
 * Time: 22:46
 *
 * @author anatoliy
 *
 * Объект данного класса содержит логику по взаимодействию с фрагментом экрана истории.
 */
public class HistoryFragmentController extends BaseFragmentController {
    private final static String HISTORY_ACTIVITY_CONTROLLER
            = "com.spitchenko.appsgeyser.historywindow.controller.HistoryFragmentController";
    private final static String LIST_STATE = HISTORY_ACTIVITY_CONTROLLER + ".listState";

    private final HistoryFragmentBroadcastReceiver historyFragmentBroadcastReceiver
            = new HistoryFragmentBroadcastReceiver();
    private LocalBroadcastManager localBroadcastManager;
    private ListView listView;
    private final Fragment fragment;
    private DrawerLayout drawerLayout;
    private Parcelable listState;

    public HistoryFragmentController(final Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void updateOnCreateView(final View view) {
        listView = (ListView) view.findViewById(R.id.fragment_history_list_view);
        drawerLayout = (DrawerLayout) fragment.getActivity()
                .findViewById(R.id.activity_base_drawer_layout);

        initToolbar(view);
    }

    private void initToolbar(final View view) {
        final android.support.v7.widget.Toolbar toolbar
                = (android.support.v7.widget.Toolbar) view
                .findViewById(R.id.fragment_history_toolbar);

        final AppCompatActivity activity = (AppCompatActivity) fragment.getActivity();
        activity.setSupportActionBar(toolbar);
        if (null != activity.getSupportActionBar()) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void updateOnSaveInstanceState(@NonNull final Bundle outState) {
        outState.putParcelable(LIST_STATE, listView.onSaveInstanceState());
    }

    @Override
    public void updateOnRestoreInstanceState(@Nullable final Bundle savedInstanceState) {
        if (null != savedInstanceState) {
            listState = savedInstanceState.getParcelable(LIST_STATE);
        }
    }

    @Override
    public void updateOnResume() {
        final Activity activity = fragment.getActivity();

        localBroadcastManager = LocalBroadcastManager.getInstance(activity);
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(HistoryFragmentBroadcastReceiver.getReadActionKey());
        intentFilter.addAction(HistoryFragmentBroadcastReceiver.getNoInternetExceptionKey());
        localBroadcastManager.registerReceiver(historyFragmentBroadcastReceiver, intentFilter);

        historyFragmentBroadcastReceiver.addObserver(this);

        HistoryFragmentIntentService.start(HistoryFragmentIntentService.getReadHistoryKey()
                , activity);
    }

    @Override
    public void updateOnPause() {
        if (null != localBroadcastManager) {
            localBroadcastManager.unregisterReceiver(historyFragmentBroadcastReceiver);
        }
        historyFragmentBroadcastReceiver.removeObserver(this);

        drawerLayout.closeDrawers();
    }

    /**
     * В данном методе происходит заполнение списка элементов listView данными,
     * пришедшими из historyFragmentBroadcastReceiver
     * @param parcelables - список элементов для заполнения списка
     *                    (ArrayList<ResponseTrio implements Parcelable>)
     */
    void updateOnUpdate(final ArrayList<Parcelable> parcelables) {
        final ArrayList<ResponseTrio> responseTrios = new ArrayList<>();
        for (int i = 0, size = parcelables.size(); i < size; i++) {
            final Parcelable parcel = parcelables.get(i);
            if (parcel instanceof ResponseTrio) {
                responseTrios.add((ResponseTrio) parcel);
            }
        }

        Collections.reverse(responseTrios);
        final ListViewAdapter listViewAdapter
                = new ListViewAdapter(fragment.getActivity(), responseTrios);
        listView.setAdapter(listViewAdapter);
        if (null != listState) {
            listView.onRestoreInstanceState(listState);
        }
    }

    @Override
    public void updateOnNoInternetException() {
        showNetworkDialog((AppCompatActivity) fragment.getActivity());
    }
}
