package com.example.servicebrocastreceiving;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://www.youtube.com/watch?v=-DYlmsTNjpw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void downloadClicked(View view) {
        CheckBox fake = findViewById(R.id.cb_fake);
        EditText etdelay = findViewById(R.id.et_delay);
        EditText etUrl = findViewById(R.id.et_url);

        // without service
        downloadWithoutService();
    }

    private void downloadWithoutService(){
        Downloader.downloadFake(URL, 3000);
        Toast.makeText(this, "Download Complete", Toast.LENGTH_SHORT).show();
    }
}
