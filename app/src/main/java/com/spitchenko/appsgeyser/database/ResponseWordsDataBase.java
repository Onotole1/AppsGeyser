package com.spitchenko.appsgeyser.database;

import android.provider.BaseColumns;

/**
 * Date: 20.04.17
 * Time: 16:20
 *
 * @author anatoliy
 *
 * Класс содержит название таблицы и колонок для работы с базой данных
 */
final class ResponseWordsDataBase {
    static abstract class WordsEntry implements BaseColumns {
        static final String TABLE_NAME = "response_words";
        static final String INPUT_TEXT = "input_text";
        static final String RESPONSE_TEXT = "response_text";
    }
}
