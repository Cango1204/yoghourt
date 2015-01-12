package com.example.hbjia.ormlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.hbjia.ormlite.bean.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by hbjia on 2015/1/12.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper{
    private static final String TABLE_NAME = "sqlite-test.db";

    private Dao<User, Integer> userDao;

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getHelper(Context context) {
        if(instance == null) {
            synchronized (DatabaseHelper.class) {
                if(instance == null) {
                    instance = new DatabaseHelper(context);
                }
            }
        }
        return instance;
    }

    public Dao<User, Integer> getUserDao() throws SQLException {
        if(userDao == null) {
            userDao = getDao(User.class);
        }
        return userDao;
    }

    @Override
    public void close() {
        super.close();
        userDao = null;
    }
}
