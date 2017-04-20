package com.spitchenko.appsgeyser.historywindow.controller;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.spitchenko.appsgeyser.model.ResponseTrio;

import java.util.List;

/**
 * Date: 20.04.17
 * Time: 23:00
 *
 * @author anatoliy
 */
public class ListViewAdapter extends ArrayAdapter<ResponseTrio> {
    public ListViewAdapter(@NonNull final Context context, @LayoutRes final int resource
            , @NonNull final List<ResponseTrio> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView
            , @NonNull final ViewGroup parent) {
        return super.getView(position, convertView, parent);

        /*final View v = convertView;

        if (v == null) {
            final LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.itemlistrow, null);
        }

        final ResponseTrio p = getItem(position);

        if (p != null) {
            final TextView tt1 = (TextView) v.findViewById(R.id.id);
            final TextView tt2 = (TextView) v.findViewById(R.id.categoryId);

            if (tt1 != null) {
                tt1.setText(p.getId());
            }

            if (tt2 != null) {
                tt2.setText(p.getCategory().getId());
            }
        }

        return v;*/
    }
}
