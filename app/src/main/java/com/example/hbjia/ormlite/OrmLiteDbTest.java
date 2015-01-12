package com.example.hbjia.ormlite;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.hbjia.ormlite.bean.User;
import com.example.hbjia.ormlite.db.DatabaseHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by hbjia on 2015/1/12.
 */
public class OrmLiteDbTest extends AndroidTestCase{
    public void testAddUser(){
        User u1 = new User("hbjia", "soccer");
        DatabaseHelper helper = DatabaseHelper.getHelper(getContext());
        try {
            helper.getUserDao().create(u1);
            u1 = new User("hbjia1", "soccer1");
            helper.getUserDao().create(u1);
            u1 = new User("hbjia2", "soccer2");
            helper.getUserDao().create(u1);
            u1 = new User("hbjia3", "soccer3");
            helper.getUserDao().create(u1);
            u1 = new User("hbjia4", "soccer4");
            helper.getUserDao().create(u1);

            testList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testList() {
        DatabaseHelper helper = DatabaseHelper.getHelper(getContext());
        try {
            List<User> users = helper.getUserDao().queryForAll();
            Log.i("OrmLiteDbTest", users.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
