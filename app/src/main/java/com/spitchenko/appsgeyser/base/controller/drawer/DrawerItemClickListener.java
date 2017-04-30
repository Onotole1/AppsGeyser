package com.spitchenko.appsgeyser.base.controller.drawer;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.spitchenko.appsgeyser.base.userinterface.BaseActivity;
import com.spitchenko.appsgeyser.model.DrawerPair;

import static com.spitchenko.appsgeyser.R.string.drawer_layout_textView_description_add;
import static com.spitchenko.appsgeyser.R.string.drawer_layout_textView_description_history;

/**
 * Date: 21.04.17
 * Time: 11:03
 *
 * @author anatoliy
 *
 * Объект данного класса подписывается на изменения drawerLayout
 * и обрабатывает нажатия на его элементы
 */
public class DrawerItemClickListener implements ListView.OnItemClickListener {
    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position
            , final long id) {
        final DrawerPair pair = (DrawerPair) ((ListView) parent).getAdapter().getItem(position);
        final String pairDescription = pair.getDescription();
        final Context context = view.getContext();
        if (pairDescription.equals(context
                .getString(drawer_layout_textView_description_add))) {
            ((BaseActivity)view.getContext()).setMainFragment();
        } else if (pairDescription.equals(context
                .getString(drawer_layout_textView_description_history))) {
            ((BaseActivity)view.getContext()).setHistoryFragment();
        }

    }
}
