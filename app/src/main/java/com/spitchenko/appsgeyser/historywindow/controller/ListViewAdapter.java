package com.spitchenko.appsgeyser.historywindow.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.spitchenko.appsgeyser.R;
import com.spitchenko.appsgeyser.model.ResponseTrio;

import java.util.List;

/**
 * Date: 20.04.17
 * Time: 23:00
 *
 * @author anatoliy
 *
 * Адаптер для отображения сообщений из базы данных в спиок listView
 */
class ListViewAdapter extends ArrayAdapter<ResponseTrio> {

    ListViewAdapter(@NonNull final Context context
            , @NonNull final List<ResponseTrio> objects) {
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
            workView = layoutInflater.inflate(R.layout.activity_history_list_element, parent, false);
        }

        final ResponseTrio responseTrio = getItem(position);

        if (responseTrio != null) {
            final TextView listTextTextView
                    = (TextView) workView.findViewById(R.id.activity_history_list_element_text);
            final TextView tt2
                    = (TextView) workView
                    .findViewById(R.id.activity_history_list_element_language);

            if (null != listTextTextView) {
                listTextTextView.setText(responseTrio.getInputText());
            }

            if (null != tt2) {
                tt2.setText(responseTrio.getLanguage());
            }
        }
        return workView;
    }
}
