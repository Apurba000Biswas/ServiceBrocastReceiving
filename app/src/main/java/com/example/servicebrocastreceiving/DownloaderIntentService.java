package com.example.servicebrocastreceiving;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import static com.example.servicebrocastreceiving.DownloaderService.DOWNLOAD_BROADCAST_ACTION;


public class DownloaderIntentService extends IntentService {

    public DownloaderIntentService() {
        super("DownloaderIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra("url");
        int delay = intent.getIntExtra("delay", 0);
        Downloader.downloadFake(url, delay);

        Intent done = new Intent();
        done.setAction(DOWNLOAD_BROADCAST_ACTION);
        done.putExtra("url", url);
        sendBroadcast(done);

        popUpNotification(url);
    }

    private void popUpNotification(String url){
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("Download Complete")
                .setContentText(url)
                .setSmallIcon(R.drawable.ic_download);

        // make it clickable
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("url", url);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1234, notification);
    }

}
