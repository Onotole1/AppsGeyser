package com.spitchenko.appsgeyser.mainwindow.controller;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.spitchenko.appsgeyser.R;
import com.spitchenko.appsgeyser.base.controller.BaseFragmentController;
import com.spitchenko.appsgeyser.base.controller.drawer.CustomDrawerListener;

import lombok.NonNull;

/**
 * Date: 20.04.17
 * Time: 15:32
 *
 * @author anatoliy
 *
 * Объект данного класса содержит логику по взаимодействию с фрагментом главного экрана.
 */
public final class MainFragmentController extends BaseFragmentController {
    private final Fragment fragment;
    private LocalBroadcastManager localBroadcastManager;
    private final MainActivityBroadcastReceiver mainActivityBroadcastReceiver
            = new MainActivityBroadcastReceiver();
    private DrawerLayout drawerLayout;
    private EditText editText;

    public MainFragmentController(final Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void updateOnCreateView(final View view) {
        editText = (EditText) view.findViewById(R.id.fragment_main_editText);
        initToolbar(view);
        initFloatingActionButton(view);
        setDrawerListener();
    }

    private void initToolbar(final View view) {

        final android.support.v7.widget.Toolbar toolbar
                = (android.support.v7.widget.Toolbar) view
                .findViewById(R.id.fragment_main_toolbar);

        final AppCompatActivity activity = (AppCompatActivity) fragment.getActivity();
        activity.setSupportActionBar(toolbar);
    }

    private void setDrawerListener() {
        final Activity activity = fragment.getActivity();

        drawerLayout = (DrawerLayout) activity.findViewById(R.id.activity_base_drawer_layout);
        final CustomDrawerListener customDrawerListener
                = new CustomDrawerListener(editText);
        drawerLayout.addDrawerListener(customDrawerListener);
    }


    private void initFloatingActionButton(final View view) {
        final FloatingActionButton fab = (FloatingActionButton) view
                .findViewById(R.id.fragment_main_floatingActionButton);

        final Drawable drawable = fab.getDrawable();
        drawable.setColorFilter(new
                PorterDuffColorFilter(Color.WHITE,PorterDuff.Mode.SRC_ATOP));
        fab.setImageDrawable(drawable);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String inputText = editText.getText().toString();
                if (!inputText.isEmpty()) {
                    MainActivityIntentService.start(MainActivityIntentService.getLanguageDetectKey()
                            , fragment.getActivity(), inputText);
                } else {
                    createErrorDialog(fragment.getString(R.string.activity_main_toast_text_empty));
                }
            }
        });
    }

    @Override
    public final void updateOnResume() {
        localBroadcastManager = LocalBroadcastManager.getInstance(fragment.getActivity());
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
                = fragment.getFragmentManager().beginTransaction();
        fragmentTransaction.add(responseShowDialog, ResponseShowDialog.getResponseShowDialogKey());
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public final void updateOnSaveInstanceState(@NonNull final Bundle outState) {

    }

    @Override
    public final void updateOnRestoreInstanceState(@Nullable final Bundle savedInstanceState) {

    }

    @Override
    public void updateOnNoInternetException() {
        showNetworkDialog((AppCompatActivity) fragment.getActivity());
    }

    void updateOnLengthException() {
       createErrorDialog(fragment
                .getString(R.string.error_show_dialog_text_length_exception));
    }

    private void createErrorDialog(final String message) {
        final Bundle arguments = new Bundle();
        arguments.putString(ErrorShowDialog.getErrorKey(), message);

        final ErrorShowDialog errorShowDialog = new ErrorShowDialog();
        errorShowDialog.setArguments(arguments);

        final FragmentTransaction fragmentTransaction
                = fragment.getFragmentManager().beginTransaction();
        fragmentTransaction.add(errorShowDialog, ErrorShowDialog.getErrorShowDialogKey());
        fragmentTransaction.commitAllowingStateLoss();
    }
}
