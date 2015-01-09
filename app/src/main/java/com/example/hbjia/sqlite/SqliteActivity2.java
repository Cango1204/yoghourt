package com.example.hbjia.sqlite;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.hbjia.http.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqliteActivity2 extends Activity {

    private DBManager manager;
    private ListView listView;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_activity2);

        listView = (ListView) findViewById(R.id.listView);
        manager = new DBManager(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.closeDB();
    }

    public void add(View view) {
        ArrayList<Student> students = new ArrayList<Student>();
        Student student1 = new Student("Ella", 22, "lively girl");
        Student student2 = new Student("Jenny", 23, "beautiful girl");
        Student student3 = new Student("Kelly", 23, "sexy girl");
        Student student4 = new Student("Jane", 25, "a pretty woman");

        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);

        manager.addStudent(students);
    }

    public void update(View view) {
        Student student = new Student();
        student.name = "Jenny";
        student.age = 30;
        manager.updateAge(student);
    }

    public void delete(View view) {
//        Student student = new Student();
//        student.age = 30;
//        manager.deleteOldStudent(student);
        manager.deleteAll();
        adapter.notifyDataSetChanged();
    }

    public void query(View view) {
        List<Student> students = manager.query();
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (Student student : students) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name", student.name);
            map.put("info", student.info);
            list.add(map);
        }
        adapter = new SimpleAdapter(this, list,
                android.R.layout.simple_list_item_2, new String[]{"name", "info"},
                new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sqlite_activity2, menu);
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
