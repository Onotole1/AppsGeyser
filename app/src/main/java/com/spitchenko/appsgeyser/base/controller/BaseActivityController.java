package com.spitchenko.appsgeyser.base.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
@SuppressWarnings("deprecation")
public class BaseActivityController extends AppCompatActivity {
    private final AppCompatActivity activity;
    private DrawerLayout drawerLayout;
    private ListView drawerListView;

    public BaseActivityController(final AppCompatActivity activity) {
        this.activity = activity;
    }

    /**
     * Метод содержит логику по инициализации элементов представления
     * @param savedInstanceState - сохранённое состояние при повороте экрана
     */
    public void updateOnCreate(@Nullable final Bundle savedInstanceState) {
        activity.setContentView(R.layout.activity_base);

        initDrawer();

        if (null == savedInstanceState) {
            final FragmentManager manager = activity.getFragmentManager();
            final FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.add(R.id.activity_base_container, new MainFragment()
                    , MainFragment.getMainFragment());
            fragmentTransaction.commit();

            drawerListView.post(new Runnable() {
                @Override
                public void run() {
                    selectMainFragment();
                }
            });

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
                .findViewById(R.id.activity_base_drawer_layout);
        drawerListView = (ListView) activity
                .findViewById(R.id.activity_base_drawer_list_view);

        drawerListView.setAdapter(new DrawerListViewAdapter(activity, drawerPairs));
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
        final LayoutInflater inflater = activity.getLayoutInflater();
        final RelativeLayout header = (RelativeLayout)inflater.inflate(R.layout.drawer_header
                , drawerListView, false);
        drawerListView.addHeaderView(header, null, false);


        if (isMainFragmentOnTheWindow()) {
            selectMainFragment();
        } else if (isHistoryFragmentOnTheWindow()) {
            selectHistoryFragment();
        }
    }

    private boolean isMainFragmentOnTheWindow() {
        final FragmentManager manager = activity.getFragmentManager();
        final Fragment fragmentByTag = manager.findFragmentByTag(MainFragment.getMainFragment());
        return null != fragmentByTag;
    }

    private boolean isHistoryFragmentOnTheWindow() {
        final FragmentManager manager = activity.getFragmentManager();
        final Fragment fragmentByTag = manager.findFragmentByTag(HistoryFragment.getHistoryFragment());
        return null != fragmentByTag;
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
                manager.popBackStackImmediate();
                replaceMainFragment(manager);
            }
        }

        selectMainFragment();
    }

    private void selectMainFragment() {
        View mainFragmentItem = drawerListView.getChildAt(1);
        mainFragmentItem.setBackgroundColor(activity.getResources().getColor(R.color.selected));

        TextView mainFragmentText
                = (TextView) mainFragmentItem.findViewById(R.id.drawer_element_textView_description);
        mainFragmentText.setTextColor(activity.getResources().getColor(R.color.colorPrimary));

        ImageView mainFragmentIcon
                = (ImageView) mainFragmentItem.findViewById(R.id.drawer_element_imageView_icon);

        final Drawable mainFragmentIconDrawable = mainFragmentIcon.getDrawable();
        mainFragmentIconDrawable.setColorFilter(new PorterDuffColorFilter(activity.getResources()
                .getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP));
        mainFragmentIcon.setImageDrawable(mainFragmentIconDrawable);

        View historyFragmentItem = drawerListView.getChildAt(2);
        historyFragmentItem.setBackgroundColor(Color.TRANSPARENT);

        TextView historyFragmentText
                = (TextView) historyFragmentItem.findViewById(R.id.drawer_element_textView_description);
        historyFragmentText.setTextColor(Color.GRAY);

        ImageView historyFragmentIcon
                = (ImageView) historyFragmentItem.findViewById(R.id.drawer_element_imageView_icon);

        final Drawable historyFragmentIconDrawable = historyFragmentIcon.getDrawable();
        historyFragmentIconDrawable.setColorFilter(new PorterDuffColorFilter(Color.GRAY
                , PorterDuff.Mode.SRC_ATOP));
        historyFragmentIcon.setImageDrawable(historyFragmentIconDrawable);
    }

    private void selectHistoryFragment() {
        View mainFragmentItem = drawerListView.getChildAt(1);
        mainFragmentItem.setBackgroundColor(Color.TRANSPARENT);

        TextView mainFragmentText
                = (TextView) mainFragmentItem.findViewById(R.id.drawer_element_textView_description);
        mainFragmentText.setTextColor(Color.GRAY);

        ImageView mainFragmentIcon
                = (ImageView) mainFragmentItem.findViewById(R.id.drawer_element_imageView_icon);

        final Drawable mainFragmentIconDrawable = mainFragmentIcon.getDrawable();
        mainFragmentIconDrawable.setColorFilter(new PorterDuffColorFilter(Color.GRAY
                , PorterDuff.Mode.SRC_ATOP));
        mainFragmentIcon.setImageDrawable(mainFragmentIconDrawable);

        View historyFragmentItem = drawerListView.getChildAt(2);
        historyFragmentItem.setBackgroundColor(activity.getResources().getColor(R.color.selected));

        TextView historyFragmentText
                = (TextView) historyFragmentItem.findViewById(R.id.drawer_element_textView_description);
        historyFragmentText.setTextColor(activity.getResources().getColor(R.color.colorPrimary));

        ImageView historyFragmentIcon
                = (ImageView) historyFragmentItem.findViewById(R.id.drawer_element_imageView_icon);

        final Drawable historyFragmentIconDrawable = historyFragmentIcon.getDrawable();
        historyFragmentIconDrawable.setColorFilter(new PorterDuffColorFilter(activity.getResources()
                .getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP));
        historyFragmentIcon.setImageDrawable(historyFragmentIconDrawable);
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
                manager.popBackStackImmediate();
                replaceHistoryFragment(manager);
            }
        }

        selectHistoryFragment();
    }

    private void replaceHistoryFragment(final FragmentManager fragmentManager) {
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_base_container, new HistoryFragment()
                , HistoryFragment.getHistoryFragment());
        fragmentTransaction.addToBackStack(HistoryFragment.getHistoryFragment());
        fragmentTransaction.commit();
    }

    public void updateOnSupportNavigateUp() {
        final FragmentManager manager = activity.getFragmentManager();
        manager.popBackStackImmediate();

        if (isMainFragmentOnTheWindow()) {
            selectMainFragment();
        } else if (isHistoryFragmentOnTheWindow()) {
            selectHistoryFragment();
        }
    }
}
