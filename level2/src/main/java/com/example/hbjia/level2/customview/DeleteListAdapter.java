package com.example.hbjia.level2.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hbjia.level2.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by hbjia on 2015/1/29.
 */
public class DeleteListAdapter extends BaseAdapter{

    private Context mContext;
    private List<String> mDatas;
    private LayoutInflater mInflater;

    public DeleteListAdapter(Context context, List<String> datas) {
        mContext = context;
        mDatas = datas;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View contentView = view;
        if(contentView == null) {
            contentView = mInflater.inflate(R.layout.delete_list_view_item, null);
        }
        TextView textView = (TextView) contentView.findViewById(R.id.id_textView);
        textView.setText(mDatas.get(i));
        return contentView;
    }
}
