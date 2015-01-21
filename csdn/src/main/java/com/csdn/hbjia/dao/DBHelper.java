package com.csdn.hbjia.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hbjia on 2015/1/21.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "csdn_app_demo";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /**
         * id,title,link,date,imgLink,content,newstype
         */
        String sql = "create table tb_newsItem(_id integer primary key autoincrement, " +
                "title text, link text, date text, imgLink text, content text, newstype integer);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
