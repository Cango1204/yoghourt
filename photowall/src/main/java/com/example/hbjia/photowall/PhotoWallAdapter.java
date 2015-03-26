package com.example.hbjia.photowall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hbjia on 2015/3/10.
 */
public class PhotoWallAdapter extends ArrayAdapter<String> implements AbsListView.OnScrollListener{

    private Set<BitmapWorkerTask> taskSet;

    private LruCache<String, Bitmap> mMemoryCache;

    private GridView mPhotoWall;

    private int mFirstVisibleItem;

    private int mVisibleItemCount;

    private boolean isFirstEnter = true;

    public PhotoWallAdapter(Context context, int resource, String[] objects, GridView photowall) {
        super(context, resource, objects);
        mPhotoWall = photowall;
        taskSet = new HashSet<BitmapWorkerTask>();

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
        mPhotoWall.setOnScrollListener(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String url = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.photo_layout, null);
        } else {
            view = convertView;
        }
        final ImageView photo = (ImageView) view.findViewById(R.id.photo);

        photo.setTag(url);
        setImageView(url, photo);
        return view;
    }

    private void setImageView(String imageUrl, ImageView imageView) {
        Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
        if(bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.empty);
        }
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if(getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrllState) {
        if(scrllState == SCROLL_STATE_IDLE) {
            loadBitmap(mFirstVisibleItem, mVisibleItemCount);
        } else {
            cancelAllTasks();
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;
        mVisibleItemCount = visibleItemCount;

        if(isFirstEnter && visibleItemCount > 0) {
            loadBitmap(firstVisibleItem, visibleItemCount);
            isFirstEnter = false;
        }
    }

    private void loadBitmap(int firstVisibleItem, int visibleItemCount) {
        for(int i = firstVisibleItem; i < visibleItemCount; i++) {
            String imageUrl = Images.imageThumbUrls[i];
            Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
            if(bitmap == null) {
                BitmapWorkerTask task = new BitmapWorkerTask();
                taskSet.add(task);
                task.execute(imageUrl);
            } else {
                ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(imageUrl);
                if(imageView != null && bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    public void cancelAllTasks() {
        if(taskSet != null) {
            for(BitmapWorkerTask task : taskSet) {
                task.cancel(false);
            }
        }
    }

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

        private String imageUrl;

        @Override
        protected Bitmap doInBackground(String... strings) {
            imageUrl = strings[0];

            Bitmap bitmap = downloadBitmap(imageUrl);
            if(bitmap != null) {
                addBitmapToMemoryCache(strings[0], bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(imageUrl);
            if(imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            taskSet.remove(this);
        }

        private Bitmap downloadBitmap(String imageUrl) {
            Bitmap bitmap = null;
            HttpURLConnection con = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);
                bitmap = BitmapFactory.decodeStream(con.getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }
}
