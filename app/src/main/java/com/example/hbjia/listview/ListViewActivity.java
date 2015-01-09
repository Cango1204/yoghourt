package com.example.hbjia.listview;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hbjia.http.R;

import java.util.ArrayList;

public class ListViewActivity extends ListActivity implements AbsListView.OnScrollListener {

    private ListView listView;
    private int visibleLastIndex = 0;
    private int visibleItemCount;
//    private ListViewAdapter adapter;
    private MyAdapter adapter;
    private View loadMoreView;
    private Button loadMoreButton;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        loadMoreView = getLayoutInflater().inflate(R.layout.load_more, null);
        loadMoreButton = (Button) loadMoreView.findViewById(R.id.loadMoreButton);

        listView = getListView();
        listView.addFooterView(loadMoreButton);

        initAdapter();

//        listView.setAdapter(adapter);
        setListAdapter(adapter);

        listView.setOnScrollListener(this);
    }

    private void initAdapter(){
        ArrayList<String> items = new ArrayList<String>();
        for(int i = 0; i < 10; i++) {
            items.add(String.valueOf(i + 1));
        }
//        adapter = new ListViewAdapter(this, items);
        adapter = new MyAdapter();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_view, menu);
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

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        int itemsLastIndex = adapter.getCount() - 1;
        int lastIndex = itemsLastIndex + 1;
        if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && this.visibleLastIndex == lastIndex) {
            Log.w("LOADMORE", "Loading...");
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.visibleItemCount = visibleItemCount;
        this.visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
    }

    public void loadMore(View view) {
        loadMoreButton.setText("loading...");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
                adapter.notifyDataSetChanged();

                listView.setSelection(visibleLastIndex - visibleItemCount + 1);

                loadMoreButton.setText("Load More");
            }
        }, 2000);
    }

    private void loadData(){
        int count = adapter.getCount();
        for(int i = count; i < count + 10; i++) {
//            adapter.addItem(String.valueOf(i + 1));
        }
    }

    public class MyAdapter extends BaseAdapter{

        public MyAdapter(){

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            TextView result = (TextView) view;
            if(result == null) {
                result = new TextView(ListViewActivity.this);
                result.setTextAppearance(ListViewActivity.this, android.R.style.TextAppearance_Large);
                AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                        AbsListView.LayoutParams.WRAP_CONTENT);
                result.setLayoutParams(layoutParams);
                result.setGravity(Gravity.CENTER);
            }
            result.setText(position);
            return result;
        }
    }
}
