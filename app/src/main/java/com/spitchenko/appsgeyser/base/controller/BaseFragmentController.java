package com.spitchenko.appsgeyser.base.controller;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import lombok.NonNull;

/**
 * Date: 29.04.17
 * Time: 13:37
 *
 * @author anatoliy
 */
public abstract class BaseFragmentController {
    /**
     * Метод содержит логику по инициализации элементов представления
     * @param view - раздутый элемент фрагмента
     */
    public abstract void updateOnCreateView(final View view);
    /**
     * Метод вызывается при прохождении жизненного цикла фрагмента для сохранения состояния
     * элементов представления
     * @param outState - объект для записи состояния
     */
    public abstract void updateOnSaveInstanceState(@NonNull final Bundle outState);
    /**
     * Метод вызывается для восстановления состояния элементов представления
     */
    public abstract void updateOnRestoreInstanceState(@NonNull final Bundle savedInstanceState);
    /**
     * Данный метод вызывается соответственно очередности жизненного цикла фрагмента
     * и содержит логику по подписке на широковещательные сообщения (аналог конструктора)
     */
    public abstract void updateOnResume();

    /**
     * Данный метод вызывается соответственно очередности жизненного цикла фрагмента
     * и содержит логику по отписыванию от широковещательных сообщения (аналог деструктора)
     */
    public abstract void updateOnPause();

    /**
     * Показать диалог обработки ситуации отсутствия интернета
     */
    public abstract void updateOnNoInternetException();

    protected void showNetworkDialog(final AppCompatActivity activity) {
        final NoInternetDialog noInternetDialog = new NoInternetDialog();
        final FragmentManager fragmentManager = activity.getFragmentManager();
        final android.app.FragmentTransaction fragmentTransaction
                = fragmentManager.beginTransaction();
        fragmentTransaction.add(noInternetDialog, NoInternetDialog.getNoInternetDialogKey());
        fragmentTransaction.commitAllowingStateLoss();
    }
}
