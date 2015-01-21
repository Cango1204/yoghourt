package com.csdn.hbjia;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.bean.CommonException;
import com.zhy.bean.NewsItem;
import com.zhy.biz.NewsItemBiz;
import com.zhy.csdn.Constaint;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MainFragment extends Fragment implements IXListViewRefreshListener, IXListViewLoadMore{

    private static final int LOAD_MORE = 0x110;
    private static final int LOAD_REFRESH = 0x111;
    private static final int TIP_ERROR_NO_NETWORK = 0x112;
    private static final int TIP_ERROR_SERVER = 0x113;

    private boolean isFirstIn = true;
    private boolean isConnNet = false;
    private boolean isLoadingDataFromNetwork;

    //默认类型
    private int newsType = Constaint.NEWS_TYPE_YEJIE;
    //当前页
    private int currentPage = 1;
    //新闻处理业务类
    private NewsItemBiz mNewsItemBiz;
    //扩展的ListView
    private XListView mXlistView;
    //数据源
    private List<NewsItem> mDatas = new ArrayList<NewsItem>();
    //适配器
    private NewsItemAdapter mAdapter;

    public MainFragment(int newsType) {
        // Required empty public constructor
        this.newsType = newsType;
        mNewsItemBiz = new NewsItemBiz();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_item_fragment_main, null);
//        TextView textView = (TextView) view.findViewById(R.id.id_tip);
//        textView.setText(TabAdapter.TITLES[this.newsType]);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new NewsItemAdapter(getActivity(), mDatas);

        mXlistView = (XListView) getView().findViewById(R.id.id_xlistview);
        mXlistView.setAdapter(mAdapter);
        mXlistView.setPullRefreshEnable(this);
        mXlistView.setPullLoadEnable(this);

        mXlistView.startRefresh();
    }

    @Override
    public void onRefresh() {
        new LoadDatasTask().execute();
    }

    @Override
    public void onLoadMore() {

    }

    class LoadDatasTask extends AsyncTask<Integer, Void, Integer>{

        @Override
        protected Integer doInBackground(Integer... voids) {
            try {
                List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
                mDatas = newsItems;
            } catch (CommonException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer aVoid) {
            mAdapter.addAll(mDatas);
            mAdapter.notifyDataSetChanged();
            mXlistView.stopRefresh();
        }
    }
}
