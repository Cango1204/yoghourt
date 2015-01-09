package com.example.hbjia.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hbjia on 2014/12/15.
 */
public class DBManager{
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void addStudent(List<Student> students) {
        database.beginTransaction();
        for(Student student : students) {
            database.execSQL("INSERT INTO student VALUES(null, ?, ?, ?)",
                    new Object[]{student.name, student.age, student.info});
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void updateAge(Student student) {
        ContentValues cv = new ContentValues();
        cv.put("age", student.age);
        database.update("student", cv, "name=?", new String[]{student.name});
    }

    public void deleteOldStudent(Student student) {
        database.delete("student", "age >= ?", new String[]{String.valueOf(student.age)});
    }

    public void deleteAll() {
        database.delete("student", null, null);
    }

    public List<Student> query() {
        ArrayList<Student> students = new ArrayList<Student>();
        Cursor c = queryTheCursor();
        while(c.moveToNext()) {
            Student student = new Student();
            student._id = c.getInt(c.getColumnIndex("_id"));
            student.name = c.getString(c.getColumnIndex("name"));
            student.age = c.getInt(c.getColumnIndex("age"));
            student.info = c.getString(c.getColumnIndex("info"));
            students.add(student);
        }
        c.close();
        return students;
    }

    public Cursor queryTheCursor() {
        Cursor c = database.rawQuery("SELECT * FROM student", null);
        return c;
    }

    public void closeDB() {
        database.close();
    }
}
