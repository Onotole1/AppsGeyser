package com.spitchenko.appsgeyser.historywindow.controller;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.spitchenko.appsgeyser.R;
import com.spitchenko.appsgeyser.base.controller.BaseActivityController;
import com.spitchenko.appsgeyser.base.controller.drawer.DrawerItemClickListener;
import com.spitchenko.appsgeyser.base.controller.drawer.DrawerListViewAdapter;
import com.spitchenko.appsgeyser.model.DrawerPair;
import com.spitchenko.appsgeyser.model.ResponseTrio;

import java.util.ArrayList;

import lombok.NonNull;

/**
 * Date: 20.04.17
 * Time: 22:46
 *
 * @author anatoliy
 *
 * Объект данного класса содержит логику по взаимодействию с активностью.
 */
public class HistoryActivityController implements BaseActivityController {
    private final static String HISTORY_ACTIVITY_CONTROLLER
            = "com.spitchenko.appsgeyser.historywindow.controller.HistoryActivityController";
    private final static String LIST_STATE = HISTORY_ACTIVITY_CONTROLLER + ".listState";

    private final HistoryActivityBroadcastReceiver historyActivityBroadcastReceiver
            = new HistoryActivityBroadcastReceiver();
    private LocalBroadcastManager localBroadcastManager;
    private ListView listView;
    private final AppCompatActivity activity;
    private DrawerLayout drawerLayout;
    private Parcelable listState;

    public HistoryActivityController(final AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void updateOnCreate(@Nullable final Bundle savedInstanceState) {
        activity.setContentView(R.layout.activity_history);

        listView = (ListView) activity.findViewById(R.id.activity_history_list_view);

        initToolbar();

        initDrawer();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) activity.findViewById(R.id.activity_history_toolbar);
        toolbar.setTitle(R.string.activity_history_toolbar_title);
        activity.setSupportActionBar(toolbar);
        if (null != activity.getSupportActionBar()) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initDrawer() {
        final DrawerPair addElement = new DrawerPair(R.drawable.ic_add
                , activity.getString(R.string
                .drawer_layout_textView_description_add));
        final DrawerPair historyElement = new DrawerPair(R.drawable.ic_history
                , activity.getString(R.string
                .drawer_layout_textView_description_history));
        final DrawerPair[] drawerPairs = {addElement, historyElement};

        drawerLayout = (DrawerLayout) activity
                .findViewById(R.id.activity_history_drawer_layout);
        final ListView drawerListView = (ListView) activity
                .findViewById(R.id.activity_history_drawer_list_view);

        drawerListView.setAdapter(new DrawerListViewAdapter(activity, drawerPairs));
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
        final LayoutInflater inflater = activity.getLayoutInflater();
        final LinearLayout header = (LinearLayout)inflater.inflate(R.layout.drawer_header
                , drawerListView, false);
        drawerListView.addHeaderView(header, null, false);
    }

    @Override
    public void updateOnSaveInstanceState(@NonNull final Bundle outState) {
        outState.putParcelable(LIST_STATE, listView.onSaveInstanceState());
    }

    @Override
    public void updateOnRestoreInstanceState(@NonNull final Bundle savedInstanceState) {
        listState = savedInstanceState.getParcelable(LIST_STATE);
    }

    @Override
    public void updateOnResume() {
        localBroadcastManager = LocalBroadcastManager.getInstance(activity);
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(HistoryActivityBroadcastReceiver.getReadActionKey());
        localBroadcastManager.registerReceiver(historyActivityBroadcastReceiver, intentFilter);

        historyActivityBroadcastReceiver.addObserver(this);

        HistoryActivityIntentService.start(HistoryActivityIntentService.getReadHistoryKey()
                , activity);
    }

    @Override
    public void updateOnPause() {
        if (null != localBroadcastManager) {
            localBroadcastManager.unregisterReceiver(historyActivityBroadcastReceiver);
        }
        historyActivityBroadcastReceiver.removeObserver(this);

        drawerLayout.closeDrawers();
    }

    /**
     * В данном методе происходит заполнение списка элементов listView данными,
     * пришедшими из historyActivityBroadcastReceiver
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

        final ListViewAdapter listViewAdapter = new ListViewAdapter(activity, responseTrios);
        listView.setAdapter(listViewAdapter);
        if (null != listState) {
            listView.onRestoreInstanceState(listState);
        }
    }
}
