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


    @Override
    public void onReceive(final Context context, final Intent intent) {
        final String action = intent.getAction();
        if (action.equals(RECEIVE_ACTION)) {
            notifyObserversUpdate(intent.getStringExtra(RECEIVE_ACTION));
        } else if (action.equals(LENGTH_EXCEPTION)) {
            notifyObserversLengthException();
        }
    }

    /**
     * Метод оповещает подписчиков об ошибке длины текста
     */
    private void notifyObserversLengthException() {
        for (int i = 0, size = observers.size(); i < size; i++) {
            final MainActivityController observer = (MainActivityController) observers.get(i);
            observer.updateOnLengthException();
        }
    }


    /**
     * Метод оповещает подписчиков об успешном распознании языка текста
     * @param language - язык текста
     */
    private void notifyObserversUpdate(final String language) {
        for (int i = 0, size = observers.size(); i < size; i++) {
            final MainActivityController observer = (MainActivityController) observers.get(i);
            observer.updateOnUpdate(language);
        }
    }

    public static String getReceiveActionKey() {
        return RECEIVE_ACTION;
    }

    public static String getExceptionActionKey() {
        return LENGTH_EXCEPTION;
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
