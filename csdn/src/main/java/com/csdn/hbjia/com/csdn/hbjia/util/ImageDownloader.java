package com.csdn.hbjia.com.csdn.hbjia.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;

import com.csdn.hbjia.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.HttpClientImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.io.File;

/**
 * Created by hbjia on 2015/1/23.
 */
public class ImageDownloader {

    private static final  String TAG = "ImageDownloader";
    public static final String DIRECTORY_NAME = "/temp";

    private static ImageDownloader instance = null;
    private ImageLoader imageLoader;

    protected ImageDownloader(Context context) {
        // Exists only to defeat instantiation.
        configImageDownloader(context);
    }

    public static ImageDownloader getInstance(Context context) {
        if(instance == null) {
            instance = new ImageDownloader(context);
        }
        return instance;
    }

    /**
     * This constructor will configure loader object in order to display image.
     * @param context
     */
    private void configImageDownloader(Context context) {

        HttpParams params = new BasicHttpParams();
        // Turn off stale checking. Our connections break all the time anyway,
        // and it's not worth it to pay the penalty of checking every time.
        HttpConnectionParams.setStaleCheckingEnabled(params, false);
        // Default connection and socket timeout of 10 seconds. Tweak to taste.
        HttpConnectionParams.setConnectionTimeout(params, 10 * 1000);
        HttpConnectionParams.setSoTimeout(params, 10 * 1000);
        HttpConnectionParams.setSocketBufferSize(params, 8192);

        // Don't handle redirects -- return them to the caller. Our code
        // often wants to re-POST after a redirect, which we must do ourselves.
        HttpClientParams.setRedirecting(params, false);
        // Set the specified user agent and register standard protocols.
        HttpProtocolParams.setUserAgent(params, "some_randome_user_agent");
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

        ClientConnectionManager manager = new ThreadSafeClientConnManager(params, schemeRegistry);


        File cacheDir = StorageUtils.getOwnCacheDirectory(context, DIRECTORY_NAME);

        // Get singleton instance of ImageLoader
        imageLoader = ImageLoader.getInstance();

        // Create configuration for ImageLoader (all options are optional)
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(4 * 1024 * 1024))
                .discCache(new UnlimitedDiscCache(cacheDir))
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .defaultDisplayImageOptions(
                        new DisplayImageOptions.Builder()
                                .showStubImage(R.drawable.images)
                                .resetViewBeforeLoading()
                                .cacheInMemory()
                                .cacheOnDisc()
                                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                                .build())
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .imageDownloader(new HttpClientImageDownloader(context, new DefaultHttpClient(manager, params)))
                .build();

        // Initialize ImageLoader with created configuration.
        imageLoader.init(config);
    }


    public void displayImage(final ImageView imageView, String imageURI) {
        if(imageView == null  ||  imageURI == null) {
            Logger.e("Either of image view or image uri is null");
            return;
        }

        // Load and display image
        imageLoader.displayImage(imageURI, imageView, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {}

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {}

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//                imageView.setImageBitmap(getRoundedCornerBitmap(bitmap, 30));
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {}
        });
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
