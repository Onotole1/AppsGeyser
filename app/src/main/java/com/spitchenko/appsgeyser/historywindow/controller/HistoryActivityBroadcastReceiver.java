package com.spitchenko.appsgeyser.historywindow.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;

import com.spitchenko.appsgeyser.base.controller.BaseBroadcastReceiver;

import java.util.ArrayList;

import lombok.NonNull;

/**
 * Date: 21.04.17
 * Time: 8:48
 *
 * @author anatoliy
 *
 * Объект данного класса получает широковещательные сообщения и оповещает о них своих подписчиков
 */
public class HistoryActivityBroadcastReceiver extends BaseBroadcastReceiver {
    private final static String HISTORY_ACTIVITY_BROADCAST_RECEIVER
            = "com.spitchenko.appsgeyser.historywindow.controller.HistoryActivityBroadcastReceiver";
    private final static String READ_ACTION = HISTORY_ACTIVITY_BROADCAST_RECEIVER + ".readAction";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (intent.getAction().equals(READ_ACTION)) {
            notifyObserversUpdate(intent.getParcelableArrayListExtra(READ_ACTION));
        }
    }

    /**
     * Метод отправляет подписчикам данные, полученные из сервиса
     * @param parcelables - список элементов для отправки
     *                    (ArrayList<ResponseTrio implements Parcelable>)
     */
    private void notifyObserversUpdate(final ArrayList<Parcelable> parcelables) {
        for (int i = 0, size = observers.size(); i < size; i++) {
            final HistoryActivityController observer = (HistoryActivityController) observers.get(i);
            observer.updateOnUpdate(parcelables);
        }
    }

    public static String getReadActionKey() {
        return READ_ACTION;
    }

    /**
     * Интерфейс для отправки широковещательного сообщения
     * @param parcels - список элементов для отправки
     *                    (ArrayList<ResponseTrio implements Parcelable>)
     * @param action - действие
     * @param packageName - имя пакета
     * @param context - контекст
     */
    public static void sendToBroadcast(@NonNull final ArrayList<Parcelable> parcels
            , @NonNull final String action, @NonNull final String packageName
            , @NonNull final Context context) {
        final Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(action);
        broadcastIntent.setPackage(packageName);
        broadcastIntent.putParcelableArrayListExtra(action, parcels);
        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
    }
}
