package com.example.hbjia.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hbjia.http.R;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListTitleFragment extends ListFragment {

    public static final int REQUEST_DETAIL = 0x110;
    public static final int RESULT_CODE = 0x111;
    private List<String> mTitles = Arrays.asList("Hello", "World", "Android");
    private int mCurrentPos;
    private ArrayAdapter<String> mAdapter;

    public ListTitleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mCurrentPos = position;
        Intent intent = new Intent(getActivity(), ContentActivity.class);
        intent.putExtra(ContentFragment.ARGUMENT, mTitles.get(position));
        startActivityForResult(intent, REQUEST_DETAIL);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, mTitles));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_DETAIL) {
            mTitles.set(mCurrentPos, mTitles.get(mCurrentPos)+" -- "+
                data.getStringExtra(ContentFragment.RESPONSE));
            mAdapter.notifyDataSetChanged();
        }
    }
}
