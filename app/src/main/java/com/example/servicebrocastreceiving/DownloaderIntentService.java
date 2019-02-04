package com.example.servicebrocastreceiving;

import android.app.IntentService;
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
    }

}
