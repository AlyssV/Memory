package com.example.alyss.memory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }


    @Override protected void onPause() {
        super.onPause();
        MainActivity.backgroundMusic.pause();

    }

    @Override protected void onResume() {
        super.onResume();
        if(SystemeActivity.music_active==1) {
            MainActivity.backgroundMusic.start();
        }

    }
}
