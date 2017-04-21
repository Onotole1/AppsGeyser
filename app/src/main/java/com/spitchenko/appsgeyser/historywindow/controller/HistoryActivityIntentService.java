package com.spitchenko.appsgeyser.historywindow.controller;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.spitchenko.appsgeyser.database.ResponseWordsDataBaseHelper;

import java.util.ArrayList;

import lombok.NonNull;

/**
 * Date: 21.04.17
 * Time: 8:39
 *
 * @author anatoliy
 *
 * Данный класс выполняет ресурсоемкие действия в фоне.
 * Система андроид задаёт особый статус таким фоновым задачам. Прописан в манифесте.
 */
public class HistoryActivityIntentService extends IntentService {
    private final static String HISTORY_ACTIVITY_INTENT_SERVICE
            = "com.spitchenko.appsgeyser.historywindow.controller.HistoryActivityIntentService";
    private final static String READ_HISTORY = HISTORY_ACTIVITY_INTENT_SERVICE + ".readHistory";

    public HistoryActivityIntentService() {
        super(HISTORY_ACTIVITY_INTENT_SERVICE);
    }

    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {
        if (null != intent) {
            if (intent.getAction().equals(READ_HISTORY)) {
                readHistory();
            }
        }
    }

    /**
     * Чтение всех сообщений с базы данных и отправка широковещательного сообщения
     */
    private void readHistory() {
        final ResponseWordsDataBaseHelper responseWordsDataBaseHelper
                = new ResponseWordsDataBaseHelper(this);
        final ArrayList<Parcelable> trios = responseWordsDataBaseHelper.readAllFromWordsDb();
        HistoryActivityBroadcastReceiver.sendToBroadcast(trios
                , HistoryActivityBroadcastReceiver.getReadActionKey(), getPackageName(), this);
    }

    public static String getReadHistoryKey() {
        return READ_HISTORY;
    }

    /**
     * Интерфейс для запуска сервиса
     * @param action - действие
     * @param context - контекст
     */
    public static void start(@NonNull final String action, @NonNull final Context context) {
        final Intent intent = new Intent(context, HistoryActivityIntentService.class);
        intent.setAction(action);
        context.startService(intent);
    }
}
