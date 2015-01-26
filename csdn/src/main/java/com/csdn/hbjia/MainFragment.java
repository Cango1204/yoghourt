package com.csdn.hbjia;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.csdn.hbjia.com.csdn.hbjia.util.AppUtil;
import com.csdn.hbjia.com.csdn.hbjia.util.NetUtil;
import com.csdn.hbjia.com.csdn.hbjia.util.ToastUtil;
import com.csdn.hbjia.dao.NewsItemDao;
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

    //是否是第一次进入
    private boolean isFirstIn = true;
    /*
    是否连接网络
     */
    private boolean isConnNet = false;
    /*
    是否从网络加载数据
     */
    private boolean isLoadingDataFromNetwork;

    //默认类型
    private int newsType = Constaint.NEWS_TYPE_YEJIE;
    //当前页
    private int currentPage = 1;
    //新闻处理业务类
    private static NewsItemBiz mNewsItemBiz;
    /*
    与数据库交互
     */
    private NewsItemDao mNewsItemDao;
    //扩展的ListView
    private XListView mXlistView;
    //数据源
    private List<NewsItem> mDatas = new ArrayList<NewsItem>();
    //适配器
    private NewsItemAdapter mAdapter;

    private Context context;

//    public MainFragment(int newsType) {
//        this.newsType = newsType;
//        mNewsItemBiz = new NewsItemBiz();
//    }

    public static MainFragment getInstance(int newsType) {
        MainFragment mainFragment = new MainFragment();
        mainFragment.newsType = newsType;
        mNewsItemBiz = new NewsItemBiz();
        return mainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_item_fragment_main, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = this.getActivity();
        mNewsItemDao = new NewsItemDao(getActivity());
        mAdapter = new NewsItemAdapter(getActivity(), mDatas);

        mXlistView = (XListView) getView().findViewById(R.id.id_xlistview);
        mXlistView.setAdapter(mAdapter);
        mXlistView.setPullRefreshEnable(this);
        mXlistView.setPullLoadEnable(this);
        mXlistView.setRefreshTime(AppUtil.getRefreashTime(getActivity(), newsType));

        mXlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsItem newsItem = mDatas.get(position);
                Intent intent = new Intent(getActivity(), NewsContentActivity.class);
                intent.putExtra("url", newsItem.getLink());
                startActivity(intent);
            }
        });

        if(isFirstIn) {
            mXlistView.startRefresh();
            isFirstIn = false;
        } else {
            mXlistView.NotRefreshAtBegin();
        }
    }

    @Override
    public void onRefresh() {
        new LoadDatasTask().execute(LOAD_REFRESH);
    }

    @Override
    public void onLoadMore() {
        new LoadDatasTask().execute(LOAD_MORE);
    }

    public void loadMoreData() {
        if(isLoadingDataFromNetwork) {
            currentPage += 1;
            try {
                List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
                mNewsItemDao.add(newsItems);
                mAdapter.addAll(newsItems);
            } catch (CommonException e) {
                e.printStackTrace();
            }
        } else {
            currentPage += 1;
            List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
            mAdapter.addAll(newsItems);
        }
    }

    public Integer refreshData() {
        if(NetUtil.checkNet(context)) {
            isConnNet = true;
            try {
                List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
                mAdapter.setDatas(newsItems);

                isLoadingDataFromNetwork = true;
                AppUtil.setRefreashTime(context, newsType);
                mNewsItemDao.deleteAll(newsType);
                mNewsItemDao.add(newsItems);
            } catch (CommonException e) {
                e.printStackTrace();
                isLoadingDataFromNetwork = false;
                return TIP_ERROR_SERVER;
            }
        } else {
            isConnNet = false;
            isLoadingDataFromNetwork = false;
            List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
            mDatas = newsItems;
            return TIP_ERROR_NO_NETWORK;
        }
        return -1;
    }

    class LoadDatasTask extends AsyncTask<Integer, Void, Integer>{

        @Override
        protected Integer doInBackground(Integer... voids) {
            switch (voids[0]) {
                case LOAD_MORE:
                    loadMoreData();
                    break;
                case LOAD_REFRESH:
                    return refreshData();
            }
            return -1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            switch (result) {
                case TIP_ERROR_NO_NETWORK:
                    ToastUtil.toast(context, "No network connection!");
                    mAdapter.setDatas(mDatas);
                    mAdapter.notifyDataSetChanged();
                    break;
                case TIP_ERROR_SERVER:
                    ToastUtil.toast(context, "Server Error!");
                    break;
                default:
                    break;
            }
            mXlistView.setRefreshTime(AppUtil.getRefreashTime(context, newsType));
            mXlistView.stopRefresh();
            mXlistView.stopLoadMore();
        }
    }
}
