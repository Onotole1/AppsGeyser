package com.spitchenko.appsgeyser.base.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.spitchenko.appsgeyser.R;
import com.spitchenko.appsgeyser.model.DrawerPair;

/**
 * Date: 21.04.17
 * Time: 10:00
 *
 * @author anatoliy
 */
public class DrawerListViewAdapter extends ArrayAdapter<DrawerPair> {
    private boolean isHeader;

    public DrawerListViewAdapter(@NonNull final Context context
            , @NonNull final DrawerPair[] objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView
            , @NonNull final ViewGroup parent) {

        View workView = convertView;
            if (workView == null) {
                final LayoutInflater layoutInflater = ((AppCompatActivity) getContext())
                        .getLayoutInflater();
                workView = layoutInflater.inflate(R.layout.drawer_element, parent, false);
            }

            final DrawerPair drawerPair = getItem(position);

            if (drawerPair != null) {
                final ImageView listDrawerImageView
                        = (ImageView) workView
                        .findViewById(R.id.drawer_element_imageView_icon);
                final TextView listDrawerDescription
                        = (TextView) workView
                        .findViewById(R.id.drawer_element_textView_description);

                if (null != listDrawerImageView) {
                    listDrawerImageView.setImageResource(drawerPair.getImageId());
                }

                if (null != listDrawerDescription) {
                    listDrawerDescription.setText(drawerPair.getDescription());
                }
            }
        return workView;
    }
}
