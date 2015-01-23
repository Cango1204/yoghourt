package com.csdn.hbjia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.csdn.hbjia.com.csdn.hbjia.util.ImageDownloader;
import com.csdn.hbjia.com.csdn.hbjia.util.Logger;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zhy.bean.NewsItem;
import com.zhy.csdn.DataUtil;

import java.util.List;

/**
 * Created by hbjia on 2015/1/21.
 */
public class NewsItemAdapter extends BaseAdapter{

    private LayoutInflater mInflator;
    private List<NewsItem> mDatas;

    //使用GitHub开源ImageLoader进行加载
//    private ImageLoader imageLoader;
//    private DisplayImageOptions options;
    private ImageDownloader imageDownloader;

    public NewsItemAdapter(Context context, List<NewsItem> list){
        this.mDatas = list;
        mInflator = LayoutInflater.from(context);
        imageDownloader = ImageDownloader.getInstance(context);
//        imageLoader = ImageLoader.getInstance();
//        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
//        options = new DisplayImageOptions.Builder().showStubImage(R.drawable.images)
//                .showImageForEmptyUri(R.drawable.images).showImageOnFail(R.drawable.images)
//                .cacheInMemory().cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
//                .displayer(new FadeInBitmapDisplayer(300)).build();

    }

    public void setDatas(List<NewsItem> newsItems) {
        this.mDatas.clear();
        this.mDatas.addAll(newsItems);
    }

    public void addAll(List<NewsItem> mDatas) {
        this.mDatas.addAll(mDatas);
    }

    @Override
    public int getCount() {
        return this.mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return this.mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View contentView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(contentView == null) {
            contentView = mInflator.inflate(R.layout.news_item_yidong, null);
            viewHolder = new ViewHolder();

            viewHolder.content = (TextView) contentView.findViewById(R.id.id_content);
            viewHolder.date = (TextView) contentView.findViewById(R.id.id_date);
            viewHolder.image = (ImageView) contentView.findViewById(R.id.id_newsImg);
            viewHolder.title = (TextView) contentView.findViewById(R.id.id_title);
            contentView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) contentView.getTag();
        }

        NewsItem newsItem = mDatas.get(i);
        viewHolder.title.setText(DataUtil.ToDBC(newsItem.getTitle()));
        viewHolder.content.setText(newsItem.getContent());
        viewHolder.date.setText(newsItem.getDate());
        Logger.e("position == " + i + " title == " + DataUtil.ToDBC(newsItem.getTitle()));
        Logger.e("Image Link === " + newsItem.getImgLink());
        if(newsItem.getImgLink() != null) {
            viewHolder.image.setImageResource(View.VISIBLE);
//            imageLoader.displayImage(newsItem.getImgLink(), viewHolder.image, options);
            imageDownloader.displayImage(viewHolder.image, newsItem.getImgLink());
        } else {
            viewHolder.image.setVisibility(View.GONE);
        }
        return contentView;
    }

    private final class ViewHolder{
        TextView title;
        ImageView image;
        TextView content;
        TextView date;
    }
}
