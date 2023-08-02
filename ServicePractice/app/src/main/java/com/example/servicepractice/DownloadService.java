package com.example.servicepractice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.io.File;

public class DownloadService extends Service {
    private String downloadUrl;
    private DownloadTask downloadTask;
    private DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1,getNotification("Downloading..",progress));
        }

        @Override
        public void onSuccess() {
            downloadTask = null;
            //下载成功将前台通知关闭
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("Download Sucess",-1));
            Toast.makeText(DownloadService.this,"Download Success!!",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailed() {
            downloadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("Download onFailed",-1));
            Toast.makeText(DownloadService.this,"Download Failed!!",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPaused() {
            downloadTask = null;
            Toast.makeText(DownloadService.this,"Download onPaused!!",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancled() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this,"Download onCancled!!",Toast.LENGTH_LONG).show();
        }
    };
    private DownloadBinder mBinder = new DownloadBinder();

    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class DownloadBinder extends Binder {
        public void startDownload(String url){
            if (downloadTask == null){
                downloadUrl = url;
                downloadTask = new DownloadTask(downloadListener);
                downloadTask.execute(downloadUrl);
                startForeground(1,getNotification("Downloading...",0));//前台服务
                Toast.makeText(DownloadService.this," Downloading....",Toast.LENGTH_LONG).show();
            }
        }
        public void pauseDownload(){
            if (downloadTask != null){
                downloadTask.pauseDownload();
            }
        }
        public void cancleDownload(){
            if (downloadTask != null){
                downloadTask.cancleDownload();
            }
            if (downloadUrl != null){
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                String diretory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                File file = new File(diretory + fileName);
                if (file.exists()){
                    file.delete();
                }
                NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(1);
                stopForeground(true);
                Toast.makeText(DownloadService.this," Cancle Download!!",Toast.LENGTH_LONG).show();
            }
        }
    }
    private Notification getNotification(String title, int progress) {
        /*
            Android 8.0以上需要Notification需要设置个Channel
         */
        String CHANNEL_ONE_ID = "com.primedu.cn";
        String CHANNEL_ONE_NAME = "Channel One";
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(DownloadService.this,MainActivity.class);
        PendingIntent pendingIntent ;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(DownloadService.this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round));
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(title);
        builder.setChannelId(CHANNEL_ONE_ID);
        if (progress >= 0){
            //大于0才显示进度
            builder.setContentText(progress +"%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    }
}