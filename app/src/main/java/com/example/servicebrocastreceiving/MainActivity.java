package com.example.servicebrocastreceiving;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://www.youtube.com/watch?v=-DYlmsTNjpw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // if i was invoked from DownloaderIntentService notification
        Intent intent = getIntent();
        if(intent != null){
            String url = intent.getStringExtra("url");
            if (url != null){
                Toast.makeText(this, "Called from Notification", Toast.LENGTH_SHORT).show();
            }
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloaderService.DOWNLOAD_BROADCAST_ACTION);
        registerReceiver(new MyBroadcastReceiver(), filter);
    }

    public void downloadClicked(View view) {
        CheckBox fake = findViewById(R.id.cb_fake);
        EditText etdelay = findViewById(R.id.et_delay);
        EditText etUrl = findViewById(R.id.et_url);

        // without service
        //downloadWithoutService();
        // Download with a service
        //downloadWithService();
        // DownloadWithIntentServices
        downloadWithIntentService();
    }

    private void downloadWithIntentService(){
        Intent downloadIntent = new Intent(this, DownloaderIntentService.class);
        downloadIntent.putExtra("url", URL);
        downloadIntent.putExtra("delay", 3000);
        Toast.makeText(this, "Starting Download ....", Toast.LENGTH_SHORT).show();
        startService(downloadIntent);
    }

    private void downloadWithService(){
        Intent downloadIntent = new Intent(this, DownloaderService.class);
        downloadIntent.putExtra("url", URL);
        downloadIntent.putExtra("delay", 3000);
        Toast.makeText(this, "Starting Download ....", Toast.LENGTH_SHORT).show();
        startService(downloadIntent);
    }

    private void downloadWithoutService(){
        Downloader.downloadFake(URL, 3000);
        Toast.makeText(this, "Download Complete", Toast.LENGTH_SHORT).show();
    }





    private class MyBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String url = intent.getStringExtra("url");
            //Toast.makeText(context, "Service Finished the download of "
             //       + url, Toast.LENGTH_SHORT).show();
        }
    }
}
