package com.example.hbjia.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.hbjia.http.R;
import com.example.hbjia.prefs.PrefsActivity;

public class DownloadService extends Service {

    private static final int NOTIFY_ID = 0;
    private boolean cancelled;
    private int progress;

    private Context mContext = this;
    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private DownloadBinder binder = new DownloadBinder();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:{
                    int rate = msg.arg1;
                    if(rate < 100) {
                        RemoteViews contentView = mNotification.contentView;
                        contentView.setTextViewText(R.id.progress, rate+"%");
                        contentView.setProgressBar(R.id.notificationProgress, 100, rate, false);
                    } else {
                        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                        mNotification.contentView = null;
                        Intent intent = new Intent(mContext, PrefsActivity.class);
                        intent.putExtra("completed", "yes");

                        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        mNotification.setLatestEventInfo(mContext, "Download Completed", "File has been downloaded.", contentIntent);
                        stopSelf();
                    }
                    mNotificationManager.notify(NOTIFY_ID, mNotification);
                    break;
                }
                case 0:{
                    mNotificationManager.cancel(NOTIFY_ID);
                    break;
                }
            }
        }
    };

    public DownloadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelled = true;
    }

    private void setUpNotification(){
        int icon = R.drawable.download;
        CharSequence ticketText = "Start downloading";
        long when = System.currentTimeMillis();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(icon)
                .setTicker(ticketText)
                .setWhen(when);
        mNotification = builder.build();
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;

        RemoteViews contentView = new RemoteViews(this.getPackageName(), R.layout.layout_notification_download);
        contentView.setTextViewText(R.id.fileName, "AngryBird.apk");
        mNotification.contentView = contentView;

        Intent intent = new Intent(this, PrefsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotification.contentIntent = pendingIntent;
        mNotificationManager.notify(NOTIFY_ID, mNotification);
    }

    private void startDownload() {
        cancelled = false;
        int rate = 0;
        while(!cancelled && rate < 100) {
            try {
                Thread.sleep(500);
                rate = rate + 5;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msg = handler.obtainMessage();
            msg.what = 1;
            msg.arg1 = rate;
            handler.sendMessage(msg);

            this.progress = rate;
        }

        if(cancelled) {
            Message message = handler.obtainMessage();
            message.what = 0;
            handler.sendMessage(message);
        }
    }

    public class DownloadBinder extends Binder {
        public void start() {
            progress = 0;
            setUpNotification();

            new Thread() {
                public void run() {
                    startDownload();
                }
            }.start();
        }

        public int getProgress() {
            return progress;
        }

        public void cancel() {
            cancelled = true;
        }

        public boolean isCancelled() {
            return cancelled;
        }
    }
}
