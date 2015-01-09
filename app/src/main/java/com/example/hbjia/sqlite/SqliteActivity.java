package com.example.hbjia.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hbjia.http.R;

public class SqliteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        SQLiteDatabase db = this.openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS person");
        db.execSQL("CREATE TABLE person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age SMALLINT)");
        Person person = new Person();
        person.setName("Cango");
        person.setAge(32);

        db.execSQL("INSERT INTO person VALUES (NULL, ?, ?)", new Object[]{person.getName(), person.getAge()});

        person.setName("David");
        person.setAge(33);
        ContentValues cv = new ContentValues();
        cv.put("name", person.getName());
        cv.put("age", person.getAge());

        db.insert("person", null, cv);

        cv = new ContentValues();
        cv.put("age", 35);

        db.update("person", cv, "name=?", new String[]{"Cango"});

        Cursor c = db.rawQuery("select * from person where age >= ?", new String[]{"33"});
        while(c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            String name = c.getString(c.getColumnIndex("name"));
            int age = c.getInt(c.getColumnIndex("age"));
            Log.d("DB data", "id="+_id+" name="+name+" age="+age);
        }
        c.close();
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sqlite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
