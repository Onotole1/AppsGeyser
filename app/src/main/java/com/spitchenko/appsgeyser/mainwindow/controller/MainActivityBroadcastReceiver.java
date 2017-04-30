package com.spitchenko.appsgeyser.mainwindow.controller;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.spitchenko.appsgeyser.base.controller.BaseBroadcastReceiver;

import lombok.NonNull;

/**
 * Date: 20.04.17
 * Time: 21:46
 *
 * @author anatoliy
 *
 * Объект данного класса получает широковещательные сообщения и оповещает о них своих подписчиков
 */
public class MainActivityBroadcastReceiver extends BaseBroadcastReceiver {
    private final static String MAIN_ACTIVITY_BROADCAST_RECEIVER
            = "com.spitchenko.appsgeyser.mainwindow.controller.MainActivityBroadcastReceiver";
    private final static String RECEIVE_ACTION = MAIN_ACTIVITY_BROADCAST_RECEIVER + ".receive";
    private final static String LENGTH_EXCEPTION
            = MAIN_ACTIVITY_BROADCAST_RECEIVER + ".lengthException";
    private final static String NO_INTERNET_EXCEPTION = MAIN_ACTIVITY_BROADCAST_RECEIVER
            + ".noInternetException";


    @Override
    public void onReceive(final Context context, final Intent intent) {
        final String action = intent.getAction();
        switch (action) {
            case RECEIVE_ACTION:
                notifyObserversUpdate(intent.getStringExtra(RECEIVE_ACTION));
                break;
            case LENGTH_EXCEPTION:
                notifyObserversLengthException();
                break;
            case NO_INTERNET_EXCEPTION:
                notifyObserversNoInternet();
                break;
        }
    }

    /**
     * Метод оповещает подписчиков об ошибке длины текста
     */
    private void notifyObserversLengthException() {
        for (int i = 0, size = observers.size(); i < size; i++) {
            final MainFragmentController observer = (MainFragmentController) observers.get(i);
            observer.updateOnLengthException();
        }
    }


    /**
     * Метод оповещает подписчиков об успешном распознании языка текста
     * @param language - язык текста
     */
    private void notifyObserversUpdate(final String language) {
        for (int i = 0, size = observers.size(); i < size; i++) {
            final MainFragmentController observer = (MainFragmentController) observers.get(i);
            observer.updateOnUpdate(language);
        }
    }

    /**
     * Метод оповещает подписчиков о ситуации, когда интернет отключен
     */
    private void notifyObserversNoInternet() {
        for (int i = 0, size = observers.size(); i < size; i++) {
            final MainFragmentController observer = (MainFragmentController) observers.get(i);
            observer.updateOnNoInternetException();
        }
    }

    public static String getReceiveActionKey() {
        return RECEIVE_ACTION;
    }

    public static String getExceptionActionKey() {
        return LENGTH_EXCEPTION;
    }

    public static String getNoInternetExceptionKey() {
        return NO_INTERNET_EXCEPTION;
    }

    /**
     * Интерфейс для отправки широковещательного сообщения
     * @param action - действие
     * @param packageName - имя пакета
     * @param context - контекст
     * @param language - язык текста
     */
    public static void sendToBroadcast(@NonNull final String action
            , @NonNull final String packageName
            , @NonNull final Context context, @Nullable final String language) {
        final Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(action);
        broadcastIntent.setPackage(packageName);
        if (null != language) {
            broadcastIntent.putExtra(action, language);
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
    }
}
