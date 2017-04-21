package com.spitchenko.appsgeyser.mainwindow.controller;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Language;
import com.spitchenko.appsgeyser.database.ResponseWordsDataBaseHelper;

import java.util.HashMap;
import java.util.Map;

import lombok.NonNull;

/**
 * Date: 20.04.17
 * Time: 17:13
 *
 * @author anatoliy
 */
public class WordsIntentService extends IntentService {
    private final static String WORDS_INTENT_SERVICE
            = "com.spitchenko.appsgeyser.mainwindow.controller.WordsIntentService";
    private final static String LANGUAGE_DETECT = WORDS_INTENT_SERVICE + ".languageDetect";
    private final static String READ_ALL_WORDS = WORDS_INTENT_SERVICE + ".readAllWords";
    private final static String API_KEY = "4978e60252ae102dfe1341146bb8cc3ec4bbbd78";

    public WordsIntentService() {
        super(WORDS_INTENT_SERVICE);
    }

    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {
        if (null != intent) {
            final String intentAction = intent.getAction();
            if (intentAction.equals(READ_ALL_WORDS)) {
                readAllWords();
            } else if (intentAction.equals(LANGUAGE_DETECT)) {
                languageDetect(intent.getStringExtra(LANGUAGE_DETECT));
            }
        }
    }

    private void languageDetect(@NonNull final String inputText) {
        final AlchemyLanguage service = new AlchemyLanguage();
        service.setApiKey(API_KEY);

        final Map<String, Object> params = new HashMap<>();
        params.put(AlchemyLanguage.TEXT, inputText);

        final Language language = service.getLanguage(params).execute();
        System.out.println(language.getLanguage());
    }

    private void readAllWords() {
        final ResponseWordsDataBaseHelper responseWordsDataBaseHelper
                = new ResponseWordsDataBaseHelper(this);

    }

    public static String getLanguageDetectKey() {
        return LANGUAGE_DETECT;
    }

    static void start(@NonNull final String action, @NonNull final Context context
            , @NonNull final String inputText) {
        final Intent intent = new Intent(context, WordsIntentService.class);
        intent.setAction(action);
        intent.putExtra(action, inputText);
        context.startService(intent);
    }
}
