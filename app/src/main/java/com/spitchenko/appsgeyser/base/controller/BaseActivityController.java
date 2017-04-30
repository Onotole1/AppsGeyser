package com.spitchenko.appsgeyser.base.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.spitchenko.appsgeyser.R;
import com.spitchenko.appsgeyser.base.controller.drawer.DrawerItemClickListener;
import com.spitchenko.appsgeyser.base.controller.drawer.DrawerListViewAdapter;
import com.spitchenko.appsgeyser.historywindow.userinterface.HistoryFragment;
import com.spitchenko.appsgeyser.mainwindow.userinterface.MainFragment;
import com.spitchenko.appsgeyser.model.DrawerPair;

/**
 * Date: 20.04.17
 * Time: 16:08
 *
 * @author anatoliy
 *
 * Абстрактный класс базового контроллера для декомпозиции активности на представление и контроллер
 */
public class BaseActivityController extends AppCompatActivity {
    private final AppCompatActivity activity;
    private DrawerLayout drawerLayout;

    public BaseActivityController(final AppCompatActivity activity) {
        this.activity = activity;
    }

    /**
     * Метод содержит логику по инициализации элементов представления
     * @param savedInstanceState - сохранённое состояние при повороте экрана
     */
    public void updateOnCreate(@Nullable final Bundle savedInstanceState) {
        activity.setContentView(R.layout.activity_base);

        if (null == savedInstanceState) {
            final FragmentManager manager = activity.getFragmentManager();
            final FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.add(R.id.activity_base_container, new MainFragment()
                    , MainFragment.getMainFragment());
            fragmentTransaction.commit();
        }

        initDrawer();
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
                .findViewById(R.id.activity_base_drawer_layout);
        final ListView drawerListView = (ListView) activity
                .findViewById(R.id.activity_base_drawer_list_view);

        drawerListView.setAdapter(new DrawerListViewAdapter(activity, drawerPairs));
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
        final LayoutInflater inflater = activity.getLayoutInflater();
        final LinearLayout header = (LinearLayout)inflater.inflate(R.layout.drawer_header
                , drawerListView, false);
        drawerListView.addHeaderView(header, null, false);
    }

    public void updateOnSetMainFragment() {
        final FragmentManager manager = activity.getFragmentManager();
        final Fragment fragmentByTag = manager.findFragmentByTag(MainFragment.getMainFragment());
        if (null == fragmentByTag) {
            replaceMainFragment(manager);
        } else {
            if (fragmentByTag.isVisible()) {
                drawerLayout.closeDrawers();
            } else {
                manager.popBackStack(MainFragment.getMainFragment()
                        , FragmentManager.POP_BACK_STACK_INCLUSIVE);
                replaceMainFragment(manager);
            }
        }
    }

    private void replaceMainFragment(final FragmentManager fragmentManager) {
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_base_container, new MainFragment()
                , MainFragment.getMainFragment());
        fragmentTransaction.commit();
    }

    public void updateOnHistoryFragment() {
        final FragmentManager manager = activity.getFragmentManager();
        final Fragment fragmentByTag = manager
                .findFragmentByTag(HistoryFragment.getHistoryFragment());
        if (null == fragmentByTag) {
            replaceHistoryFragment(manager);
        } else {
            if (fragmentByTag.isVisible()) {
                drawerLayout.closeDrawers();
            } else {
                manager.popBackStack(HistoryFragment.getHistoryFragment()
                        , FragmentManager.POP_BACK_STACK_INCLUSIVE);
                replaceHistoryFragment(manager);
            }
        }
    }

    private void replaceHistoryFragment(final FragmentManager fragmentManager) {
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_base_container, new HistoryFragment()
                , HistoryFragment.getHistoryFragment());
        fragmentTransaction.addToBackStack(HistoryFragment.getHistoryFragment());
        fragmentTransaction.commit();
    }
}
