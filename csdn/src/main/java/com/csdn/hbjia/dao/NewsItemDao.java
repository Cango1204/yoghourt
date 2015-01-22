package com.csdn.hbjia.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.csdn.hbjia.com.csdn.hbjia.util.Logger;
import com.zhy.bean.NewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hbjia on 2015/1/22.
 */
public class NewsItemDao {
    private DBHelper dbHelper;

    public NewsItemDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void add(NewsItem newsItem) {
        Logger.e("add news newstype" + newsItem.getNewsType());
        String sql = "insert into tb_newsItem(title,link,date,imgLink,content,newstype) values(?,?,?,?,?,?);";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql, new Object[]{newsItem.getTitle(), newsItem.getLink(), newsItem.getDate(),
            newsItem.getImgLink(), newsItem.getContent(), newsItem.getNewsType()});
        db.close();
    }

    public void deleteAll(int newstype) {
        Logger.e("delete news" + newstype);
        String sql = "delete from tb_newsItem where newstype="+newstype;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    public void add(List<NewsItem> items){
        for(NewsItem item : items) {
            add(item);
        }
    }

    public List<NewsItem> list(int newsType, int currentPage) {
        Logger.e("newsType = " + newsType);
        Logger.e("currentPage = " + currentPage);

        List<NewsItem> newsItems = new ArrayList<NewsItem>();
        try{
            int offset = 10 * (currentPage - 1);
            String sql = "select title,link,date,imgLink,content,newstype from tb_newsItem" +
                    " where newstype=? limit ?,?";
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor c = db.rawQuery(sql, new String[]{newsType + "", offset + "", (offset + 10) + ""});
            NewsItem newsItem = null;
            while (c.moveToNext()) {
                newsItem = new NewsItem();

                String title = c.getString(0);
                String link = c.getString(1);
                String date = c.getString(2);
                String imgLink = c.getString(3);
                String content = c.getString(4);
                Integer type = c.getInt(5);

                newsItem.setTitle(title);
                newsItem.setLink(link);
                newsItem.setDate(date);
                newsItem.setImgLink(imgLink);
                newsItem.setContent(content);
                newsItem.setNewsType(type);
                newsItems.add(newsItem);
            }
            c.close();
            db.close();
            Logger.e(newsItems.size() + " newsItems.size()");
        }catch (Exception e){

        }
        return newsItems;
    }
}
