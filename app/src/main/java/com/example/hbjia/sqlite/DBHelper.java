package com.example.hbjia.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hbjia on 2014/12/15.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static String DATABASE_NAME = "test.db";
    private static int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists student (_id integer primary key autoincrement," +
                "name varchar, age integer, info TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("ALTER TABLE student ADD COLUMN other STRING");
    }
}
