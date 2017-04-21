package com.spitchenko.appsgeyser.mainwindow.controller;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.spitchenko.appsgeyser.R;

/**
 * Date: 22.04.17
 * Time: 2:11
 *
 * @author anatoliy
 */
public class ResponseShowDialog extends DialogFragment {
    private final static String RESPONSE_SHOW_DIALOG =
            "com.spitchenko.focusstart.channelwindow.controller.ChannelRefreshDialog";
    private final static String LANGUAGE = RESPONSE_SHOW_DIALOG + ".language";

    @Override
    public final Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        final Bundle input = getArguments();
        final String language = input.getString(LANGUAGE);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getActivity().getString(R.string.response_show_dialog_text) + language)
                .setPositiveButton(R.string.response_show_dialog_ok_button, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                    }
                });
        return builder.create();
    }

    public static String getResponseShowDialogKey() {
        return RESPONSE_SHOW_DIALOG;
    }

    public static String getLanguageKey() {
        return LANGUAGE;
    }
}
