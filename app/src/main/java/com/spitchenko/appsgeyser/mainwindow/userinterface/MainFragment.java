package com.spitchenko.appsgeyser.mainwindow.userinterface;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spitchenko.appsgeyser.R;
import com.spitchenko.appsgeyser.base.userinterface.BaseFragment;
import com.spitchenko.appsgeyser.mainwindow.controller.MainFragmentController;

/**
 * Date: 20.04.17
 * Time: 15:32
 *
 * @author anatoliy
 *
 * Фрагмент главного экрана. Содержит действия над подписчиками.
 */
public final class MainFragment extends BaseFragment {
    private final static String MAIN_FRAGMENT
            = "com.spitchenko.appsgeyser.mainwindow.userinterface.MainFragment";

    MainFragmentController controller = new MainFragmentController(this);

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container
            , final Bundle savedInstanceState) {
        addObserver(controller);
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        notifyObserversOnCreateView(view);
        return view;
    }

    @Override
    public void onAttach(final Context context) {
        addObserver(controller);
        super.onAttach(context);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        addObserver(controller);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        addObserver(controller);
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        removeObserver(controller);
    }

    public static String getMainFragment() {
        return MAIN_FRAGMENT;
    }
}
