package com.spitchenko.appsgeyser.mainwindow.controller;

import android.app.FragmentTransaction;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.spitchenko.appsgeyser.R;
import com.spitchenko.appsgeyser.base.controller.BaseActivityController;
import com.spitchenko.appsgeyser.base.controller.drawer.CustomDrawerListener;
import com.spitchenko.appsgeyser.base.controller.drawer.DrawerItemClickListener;
import com.spitchenko.appsgeyser.base.controller.drawer.DrawerListViewAdapter;
import com.spitchenko.appsgeyser.model.DrawerPair;

import lombok.NonNull;

/**
 * Date: 20.04.17
 * Time: 15:32
 *
 * @author anatoliy
 *
 * Объект данного класса содержит логику по взаимодействию с активностью.
 */
public final class MainActivityController extends BaseActivityController {
    private final AppCompatActivity activity;
    private LocalBroadcastManager localBroadcastManager;
    private final MainActivityBroadcastReceiver mainActivityBroadcastReceiver
            = new MainActivityBroadcastReceiver();
    private DrawerLayout drawerLayout;
    private EditText editText;

    public MainActivityController(final AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public final void updateOnCreate(@Nullable final Bundle savedInstanceState) {
        activity.setContentView(R.layout.activity_main);

        editText = (EditText) activity.findViewById(R.id.activity_main_editText);

        initToolbar();

        initDrawer();

        initFloatingActionButton();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) activity.findViewById(R.id.activity_main_toolbar);
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
                .findViewById(R.id.activity_main_drawer_layout);
        final ListView drawerListView = (ListView) activity
                .findViewById(R.id.activity_main_drawer_list_view);

        drawerListView.setAdapter(new DrawerListViewAdapter(activity, drawerPairs));
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
        final LayoutInflater inflater = activity.getLayoutInflater();
        final LinearLayout header = (LinearLayout)inflater.inflate(R.layout.drawer_header
                , drawerListView, false);
        drawerListView.addHeaderView(header, null, false);
        final CustomDrawerListener customDrawerListener = new CustomDrawerListener(editText);
        drawerLayout.addDrawerListener(customDrawerListener);
    }

    private void initFloatingActionButton() {
        final FloatingActionButton fab = (FloatingActionButton) activity
                .findViewById(R.id.activity_main_floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String inputText = editText.getText().toString();
                if (!inputText.isEmpty()) {
                    MainActivityIntentService.start(MainActivityIntentService.getLanguageDetectKey()
                            , activity, inputText);
                } else {
                    createErrorDialog(activity.getString(R.string.activity_main_toast_text_empty));
                }
            }
        });
    }

    @Override
    public final void updateOnResume() {
        localBroadcastManager = LocalBroadcastManager.getInstance(activity);
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MainActivityBroadcastReceiver.getReceiveActionKey());
        intentFilter.addAction(MainActivityBroadcastReceiver.getExceptionActionKey());
        intentFilter.addAction(MainActivityBroadcastReceiver.getNoInternetExceptionKey());
        localBroadcastManager.registerReceiver(mainActivityBroadcastReceiver, intentFilter);

        mainActivityBroadcastReceiver.addObserver(this);
    }

    @Override
    public final void updateOnPause() {
        if (null != localBroadcastManager) {
            localBroadcastManager.unregisterReceiver(mainActivityBroadcastReceiver);
        }
        mainActivityBroadcastReceiver.removeObserver(this);

        drawerLayout.closeDrawers();
    }

    /**
     * Данный метод выводит результат, полученный из широковещательного сообщения на экран
     * @param language - язык введённого текста
     */
    void updateOnUpdate(final String language) {
        final Bundle arguments = new Bundle();
        arguments.putString(ResponseShowDialog.getLanguageKey(), language);

        final ResponseShowDialog responseShowDialog = new ResponseShowDialog();
        responseShowDialog.setArguments(arguments);

        final FragmentTransaction fragmentTransaction
                = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.add(responseShowDialog, ResponseShowDialog.getResponseShowDialogKey());
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public final void updateOnSaveInstanceState(@NonNull final Bundle outState) {

    }

    @Override
    public final void updateOnRestoreInstanceState(@NonNull final Bundle savedInstanceState) {

    }

    @Override
    public void updateOnNoInternetException() {
        showNetworkDialog(activity);
    }

    void updateOnLengthException() {
        createErrorDialog(activity
                .getString(R.string.error_show_dialog_text_length_exception));
    }

    private void createErrorDialog(final String message) {
        final Bundle arguments = new Bundle();
        arguments.putString(ErrorShowDialog.getErrorKey(), message);

        final ErrorShowDialog errorShowDialog = new ErrorShowDialog();
        errorShowDialog.setArguments(arguments);

        final FragmentTransaction fragmentTransaction
                = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.add(errorShowDialog, ErrorShowDialog.getErrorShowDialogKey());
        fragmentTransaction.commitAllowingStateLoss();
    }
}
