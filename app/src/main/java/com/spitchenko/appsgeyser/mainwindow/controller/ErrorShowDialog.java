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
 * Time: 2:30
 *
 * @author anatoliy
 */
public class ErrorShowDialog extends DialogFragment {
    private final static String ERROR_SHOW_DIALOG =
            "com.spitchenko.appsgeyser.mainwindow.controller";
    private final static String ERROR = ERROR_SHOW_DIALOG + ".error";

    @Override
    public final Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        final Bundle input = getArguments();
        final String error = input.getString(ERROR);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(error)
                .setPositiveButton(R.string.show_dialog_ok_button
                        , new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                    }
                });
        return builder.create();
    }

    public static String getErrorShowDialogKey() {
        return ERROR_SHOW_DIALOG;
    }

    public static String getErrorKey() {
        return ERROR;
    }
}
