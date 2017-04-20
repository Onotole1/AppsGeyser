package com.spitchenko.appsgeyser.mainwindow.controller;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.IdentifiedLanguage;

import java.util.List;

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
    private final static String API_KEY = "4978e60252ae102dfe1341146bb8cc3ec4bbbd78";

    public WordsIntentService() {
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

    private void languageDetect(@NonNull final String inputText) {
        final LanguageTranslator translation = new LanguageTranslator();
        translation.setApiKey(API_KEY);
        final ServiceCall<List<IdentifiedLanguage>> identify = translation.identify(inputText);
        //В следующей строке я получаю исключение и ошибку с кодом 500
        final List<IdentifiedLanguage> execute = identify.execute();
        if (execute.isEmpty()); {

        }
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
