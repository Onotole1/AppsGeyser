package com.spitchenko.appsgeyser.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.spitchenko.appsgeyser.database.ResponseWordsDataBase.WordsEntry;

import lombok.NonNull;

/**
 * Date: 20.04.17
 * Time: 16:20
 *
 * @author anatoliy
 */
public class ResponseWordsDataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ResponseWords.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String SQL_CREATE_WORDS_ENTRIES =
            "CREATE TABLE " + WordsEntry.TABLE_NAME + " (" +
                    WordsEntry._ID + " INTEGER PRIMARY KEY, " +
                    WordsEntry.INPUT_TEXT + TEXT_TYPE + "," +
                    WordsEntry.RESPONSE_TEXT + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_WORDS_ENTRIES =
            "DROP TABLE IF EXISTS " + WordsEntry.TABLE_NAME;

    public ResponseWordsDataBaseHelper(@NonNull final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public final void onCreate(@NonNull final SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_WORDS_ENTRIES);
    }

    @Override
    public final void onUpgrade(final SQLiteDatabase db, final int oldVersion
            , final int newVersion) {
        db.execSQL(SQL_DELETE_WORDS_ENTRIES);
        onCreate(db);
    }
}
