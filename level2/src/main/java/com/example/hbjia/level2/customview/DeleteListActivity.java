package com.example.hbjia.level2.customview;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hbjia.level2.R;
import com.example.hbjia.level2.actionbar.AlbumFragment;
import com.example.hbjia.level2.actionbar.ArtistFragment;
import com.example.hbjia.level2.actionbar.TabListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeleteListActivity extends Activity {

    private List<String> mDatas = new ArrayList<String>();
    private DeleteListAdapter mListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_list);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab = actionBar.newTab().setText("Artist").setTabListener(new TabListener<ArtistFragment>(this, "artist", ArtistFragment.class));
        actionBar.addTab(tab);
        tab = actionBar.newTab().setText("Album").setTabListener(new TabListener<AlbumFragment>(this, "album", AlbumFragment.class));
        actionBar.addTab(tab);
        initData();
    }

    private void initData() {
        DeleteListView listView = (DeleteListView) this.findViewById(R.id.id_deleteListView);
        initList();
        mListAdapter = new DeleteListAdapter(this, mDatas);
        listView.setOnDeleteListener(new DeleteListView.OnDeleteListener() {
            @Override
            public void onDelete(int index) {
                mDatas.remove(index);
                mListAdapter.notifyDataSetChanged();
            }
        });

        listView.setAdapter(mListAdapter);
    }

    private void initList() {
        mDatas.add("Item 1");
        mDatas.add("Item 2");
        mDatas.add("Item 3");
        mDatas.add("Item 4");
        mDatas.add("Item 5");
        mDatas.add("Item 6");
        mDatas.add("Item 7");
        mDatas.add("Item 8");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
