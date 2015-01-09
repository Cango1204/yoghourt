package com.example.hbjia.listview;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.hbjia.http.R;

import java.util.List;

/**
 * Created by hbjia on 2014/12/18.
 */
public class ListViewAdapter extends BaseAdapter{

    private List<String> items;
    private LayoutInflater inflater;
    private Context context;

    public ListViewAdapter(Context context, List<String> listItems){
        this.items = listItems;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        TextView result = (TextView) view;
        if(result == null) {
            result = new TextView(this.context);
            result.setTextAppearance(this.context, android.R.style.TextAppearance_Large);
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                    AbsListView.LayoutParams.WRAP_CONTENT);
            result.setLayoutParams(layoutParams);
            result.setGravity(Gravity.CENTER);
        }
        result.setText(this.items.get(position));
        return result;
    }

    public void addItem(String item){
        this.items.add(item);
    }
}
