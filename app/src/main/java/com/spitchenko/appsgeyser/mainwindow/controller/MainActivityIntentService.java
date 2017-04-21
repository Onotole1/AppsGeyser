package com.spitchenko.appsgeyser.mainwindow.controller;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Language;
import com.ibm.watson.developer_cloud.service.exception.BadRequestException;
import com.spitchenko.appsgeyser.database.ResponseWordsDataBaseHelper;
import com.spitchenko.appsgeyser.historywindow.controller.HistoryActivityBroadcastReceiver;
import com.spitchenko.appsgeyser.utils.logger.LogCatHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.NonNull;

/**
 * Date: 20.04.17
 * Time: 17:13
 *
 * @author anatoliy
 *
 * Данный класс выполняет ресурсоемкие действия в фоне.
 * Система андроид задаёт особый статус таким фоновым задачам. Прописан в манифесте.
 */
public class MainActivityIntentService extends IntentService {
    private final static String WORDS_INTENT_SERVICE
            = "com.spitchenko.appsgeyser.mainwindow.controller.MainActivityIntentService";
    private final static String LANGUAGE_DETECT = WORDS_INTENT_SERVICE + ".languageDetect";
    private final static String API_KEY = "4978e60252ae102dfe1341146bb8cc3ec4bbbd78";

    public MainActivityIntentService() {
        super(WORDS_INTENT_SERVICE);
    }

    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {
        if (null != intent) {
            final String intentAction = intent.getAction();
            if (intentAction.equals(LANGUAGE_DETECT)) {
                languageDetect(intent.getStringExtra(LANGUAGE_DETECT));
            }
        }
    }

    /**
     * Метод по распознаванию языка текста. Использует
     * Api "com.ibm.watson.developer_cloud:java-sdk:3.7.2"
     * @param inputText - исходный текст
     */
    private void languageDetect(@NonNull final String inputText) {
        final AlchemyLanguage service = new AlchemyLanguage();
        service.setApiKey(API_KEY);

        final Map<String, Object> params = new HashMap<>();
        params.put(AlchemyLanguage.TEXT, inputText);

        final Language language;
        try {

            //Запрос на сервер. Возможны исключительные ситуации
            language = service.getLanguage(params).execute();
            //Распознынный язык
            final String detectLanguage = language.getLanguage();

            //Запись результата в базу
            final ResponseWordsDataBaseHelper responseWordsDataBaseHelper
                    = new ResponseWordsDataBaseHelper(this);
            responseWordsDataBaseHelper.writeWordToDb(inputText, detectLanguage);

            //Отправка широковещательного сообщения на главный экран
            MainActivityBroadcastReceiver.sendToBroadcast(MainActivityBroadcastReceiver
                    .getReceiveActionKey(), getPackageName(), this, detectLanguage);

            //Чтение всех элементов (в том числе новых) из базы данных
            final ArrayList<Parcelable> parcelables
                    = responseWordsDataBaseHelper.readAllFromWordsDb();
            //Отправка элементов через широковещательное сообщение на экран истории.
            //Ситуация, когда пользователь успевает перейти на экран истории пока идёт запрос
            //на сервер маловероятна, но такое требование было в задании
            HistoryActivityBroadcastReceiver.sendToBroadcast(parcelables
                    , HistoryActivityBroadcastReceiver.getReadActionKey(), getPackageName(), this);
        } catch (final BadRequestException e) {
            MainActivityBroadcastReceiver.sendToBroadcast(MainActivityBroadcastReceiver
                    .getExceptionActionKey(), getPackageName(), this, null);
            LogCatHandler.publishInfoRecord(e.getMessage());
        }

    }

    public static String getLanguageDetectKey() {
        return LANGUAGE_DETECT;
    }

    /**
     * Интерфейс запуска сервиса
     * @param action - действие
     * @param context - контекст
     * @param inputText - введённый текст
     */
    static void start(@NonNull final String action, @NonNull final Context context
            , @NonNull final String inputText) {
        final Intent intent = new Intent(context, MainActivityIntentService.class);
        intent.setAction(action);
        intent.putExtra(action, inputText);
        context.startService(intent);
    }
}
