package com.example.kevin.techscheduler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kevin on 6/22/2017.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hourlyForecast.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "forecasts";
    public static final String COLUMN_NAME_YEAR = "year";
    public static final String COLUMN_NAME_MONTH = "month";
    public static final String COLUMN_NAME_DAY = "day";
    public static final String COLUMN_NAME_HOUR = "hour";
    public static final String COLUMN_NAME_TEMP_F = "tempF";
    public static final String COLUMN_NAME_CONDITION = "condition";
    public static final String COLUMN_NAME_ICON_URL = "iconURL";
    public static final String COLUMN_NAME_HUMIDITY = "humidity";
    public static final String COLUMN_NAME_FEELS_LIKE = "feelsLike";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_YEAR + " INTEGER," +
                    COLUMN_NAME_MONTH + " INTEGER," +
                    COLUMN_NAME_DAY + " INTEGER," +
                    COLUMN_NAME_HOUR + " INTEGER," +
                    COLUMN_NAME_TEMP_F + " INTEGER," +
                    COLUMN_NAME_CONDITION + " TEXT," +
                    COLUMN_NAME_ICON_URL + " TEXT," +
                    COLUMN_NAME_HUMIDITY + " INTEGER," +
                    COLUMN_NAME_FEELS_LIKE + " INTEGER)";


    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
