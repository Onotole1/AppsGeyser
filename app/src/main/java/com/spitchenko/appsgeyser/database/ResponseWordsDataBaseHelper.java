package com.spitchenko.appsgeyser.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcelable;

import com.spitchenko.appsgeyser.database.ResponseWordsDataBase.WordsEntry;
import com.spitchenko.appsgeyser.model.ResponseTrio;

import java.util.ArrayList;

import lombok.Cleanup;
import lombok.NonNull;

/**
 * Date: 20.04.17
 * Time: 16:20
 *
 * @author anatoliy
 *
 * Класс обеспечивает доступ к базе данных для хранения истории и чтения элементов.
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

    /**
     * Запись в базу данных одного элемента
     * @param text - введённый текст
     * @param language - распознаннй язык
     */
    public void writeWordToDb(final String text, final String language) {
        @Cleanup
        final SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        final ContentValues values = new ContentValues();

        values.put(WordsEntry.INPUT_TEXT, text);
        values.put(WordsEntry.RESPONSE_TEXT, language);
        sqLiteDatabase.insert(WordsEntry.TABLE_NAME, null, values);
    }

    /**
     * Чтение всех элементов из базы данных
     * @return - возвращаемое значение в виде ArrayList<Parcelable>, но все элементы
     * являются объектами ResponseTrio (implements Parcelable). Это сделано для упрощения
     * передачи объектов через broadcast
     */
    public ArrayList<Parcelable> readAllFromWordsDb() {
        @Cleanup
        final SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        final ArrayList<Parcelable> result = new ArrayList<>();
        @Cleanup
        final Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "
                + WordsEntry.TABLE_NAME, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final ResponseTrio responseTrio = new ResponseTrio();
            responseTrio.setId(cursor.getLong(cursor.getColumnIndex(WordsEntry._ID)));
            responseTrio.setInputText(cursor.getString(cursor.getColumnIndex(WordsEntry
                    .INPUT_TEXT)));
            responseTrio.setLanguage(cursor.getString(cursor.getColumnIndex(WordsEntry
                    .RESPONSE_TEXT)));
            result.add(responseTrio);
            cursor.moveToNext();
        }

        return result;
    }
}
