package com.spitchenko.appsgeyser.mainwindow.controller;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.spitchenko.appsgeyser.database.ResponseWordsDataBaseHelper;
import com.spitchenko.appsgeyser.utils.logger.LogCatHandler;
import com.spitchenko.appsgeyser.utils.parser.XmlParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

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

    private void readAllWords() {
        final ResponseWordsDataBaseHelper responseWordsDataBaseHelper
                = new ResponseWordsDataBaseHelper(this);

    }

    private void languageDetect(@NonNull final String inputText){
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url ="http://gateway-a.watsonplatform.net/calls/text/TextGetLanguage?apikey="
                + API_KEY + "&text=" + inputText;
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url
                , createResponseListener(this)
                , createErrorListener());
        queue.add(stringRequest);
    }

    private Response.Listener<String> createResponseListener(final Context context) {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    final XmlParser xmlParser = new XmlParser(response);
                    final ResponseWordsDataBaseHelper responseWordsDataBaseHelper
                            = new ResponseWordsDataBaseHelper(context);
                    final String language = xmlParser.getLanguage();
                    responseWordsDataBaseHelper.writeWordToDb(response, language);
                    MainActivityBroadcastReceiver.sendToBroadcast(language
                            , MainActivityBroadcastReceiver.getReceiveActionKey(), getPackageName()
                            , context);
                } catch (IOException | XmlPullParserException e) {
                    LogCatHandler.publishInfoRecord(e.getMessage());
                }
            }
        };
    }

    private Response.ErrorListener createErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                LogCatHandler.publishInfoRecord(error.getMessage());
            }
        };
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
