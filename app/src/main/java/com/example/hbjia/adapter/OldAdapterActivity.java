package com.example.hbjia.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hbjia.http.R;
import com.example.hbjia.listview.ListViewActivity;

import java.util.ArrayList;
import java.util.List;

public class OldAdapterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_adapter);

        List<String> names = new ArrayList<String>();
        for(int i = 0; i < 20; i++) {
            names.add("Cango " + i);
        }
        ListView listView = (ListView) findViewById(R.id.old_list_view);
        MyAdapter myAdapter = new MyAdapter(names, this);
        listView.setAdapter(myAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_old_adapter, menu);
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

    class MyAdapter extends BaseAdapter{

        private LayoutInflater mInflater;
        private Context mContext;
        private List<String> mLists;

        private MyAdapter(List<String> lists, Context context){
            this.mInflater = LayoutInflater.from(context);
            this.mLists = lists;
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return mLists != null ? mLists.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return mLists != null ? mLists.get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
//            ViewHolder viewHolder = null;
//            if(view == null) {
//                view = mInflater.inflate(R.layout.old_list_item, viewGroup, false);
//                viewHolder = new ViewHolder();
//                viewHolder.mTextView = (TextView) view.findViewById(R.id.old_text);
//                view.setTag(viewHolder);
//            } else {
//                viewHolder = (ViewHolder) view.getTag();
//            }
//            viewHolder.mTextView.setText(this.mLists.get(i));
//            return view;
            NewViewHolder viewHolder = NewViewHolder.get(mContext, view, viewGroup, R.layout.old_list_item, i);
            TextView textView = (TextView) viewHolder.getView(R.id.old_text);
            textView.setText(mLists.get(i));
            return viewHolder.getContentView();
        }

        class ViewHolder{
            TextView mTextView;
        }
    }
}
