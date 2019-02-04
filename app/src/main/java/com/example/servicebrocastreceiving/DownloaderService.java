package com.example.servicebrocastreceiving;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class DownloaderService extends Service {
    // use a long String to send the broadcast result so thst it dose
    // not collide with any other broadcast message
    public static final String DOWNLOAD_BROADCAST_ACTION =
            "DownloaderService_FAKE_DOWNLOAD_IS_COMPLETE";


    public DownloaderService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String url = intent.getStringExtra("url");
        final  int delay = intent.getIntExtra("delay", 0);

        // without extra thread
        // withoutExtraThread(url, delay);
        // with extra thread
        withExtraThread(url, delay);

        return START_STICKY;
    }

    private void withExtraThread(final String url, final int delay){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Downloader.downloadFake(url, delay);
                // you actually can not create any UI event in a background thread
                // in Android , So the Toast is making crash here.
                // If you want to show a toast Message or anything in the Ui after service
                // done with its task, you have to Broadcast the result
                // Toast.makeText(DownloaderService.this, "Download Finished", Toast.LENGTH_SHORT).show();
                Intent done = new Intent();
                done.setAction(DOWNLOAD_BROADCAST_ACTION);
                done.putExtra("url", url);
                sendBroadcast(done);
            }
        });
        thread.start();
    }

    private void withoutExtraThread(String url, int delay){
        Downloader.downloadFake(url, delay);
        Toast.makeText(this, "Download Finished", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }
}
