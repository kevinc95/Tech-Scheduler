package com.example.kevin.techscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for managing our SQLiteDatabase of weather data.
 */
public class DatabaseManager {
    DBOpenHelper dbOpenHelper;
    SQLiteDatabase database;

    /**
     * Create an instance of DatabaseManager.
     */
    public DatabaseManager(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    /**
     * Open the database.
     **/
    public void open() {
        database = dbOpenHelper.getWritableDatabase();
    }

    /**
     * Close the database.
     **/
    public void close() {
        dbOpenHelper.close();
    }

    /**
     * Insert weather data into the database
     **/
    public void insertForecastInfo(ContentValues values) {
        database.insert(DBOpenHelper.TABLE_NAME, null, values);
    }

    /**
     * Get all the weather data of each hour for the next 36 hours
     * stored in an arraylist of string arrays.
     **/
    public ArrayList<String[]> getAllRecords() {
        Cursor cursor = database.query(DBOpenHelper.TABLE_NAME,
                new String[] {
                        DBOpenHelper.COLUMN_NAME_YEAR,
                        DBOpenHelper.COLUMN_NAME_MONTH,
                        DBOpenHelper.COLUMN_NAME_DAY,
                        DBOpenHelper.COLUMN_NAME_HOUR,
                        DBOpenHelper.COLUMN_NAME_TEMP_F,
                        DBOpenHelper.COLUMN_NAME_FEELS_LIKE,
                        DBOpenHelper.COLUMN_NAME_HUMIDITY,
                        DBOpenHelper.COLUMN_NAME_CONDITION,
                        DBOpenHelper.COLUMN_NAME_ICON_URL
                }, null, null, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<String[]> result = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            result.add(new String[] {cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8)});
            cursor.moveToNext();
        }

        return result;
    }

    /**
     * Clears the whole SQLiteDatabase
     **/
    public void deleteAll() {
        if (database.isOpen()) {
            database.execSQL("DELETE FROM " + DBOpenHelper.TABLE_NAME);
        }
    }
}
