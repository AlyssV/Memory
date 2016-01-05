package com.example.alyss.memory;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SystemeActivity extends AppCompatActivity {

    private Switch switch_music;
    static int music_active=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systeme);

        switch_music    = (Switch) findViewById(R.id.switch_music);
        final Button button_quitter = (Button) findViewById(R.id.button_quitter);

        switch_music.setChecked(MainActivity.musicPref.getBoolean(MainActivity.musicOnString, false));
        switch_music.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MainActivity.backgroundMusic.start();
                    MainActivity.backgroundMusic.setLooping(true);
                    MainActivity.musicPrefEditor.putBoolean(MainActivity.musicOnString, true);
                    MainActivity.musicPrefEditor.apply();
                    music_active=1;
                } else {
                    MainActivity.backgroundMusic.pause();
                    MainActivity.musicPrefEditor.putBoolean(MainActivity.musicOnString, false);
                    MainActivity.musicPrefEditor.apply();
                    music_active=0;

                }
            }
        });

        button_quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });
    }


    @Override protected void onPause() {
        super.onPause();
        MainActivity.backgroundMusic.pause();

    }

    @Override protected void onResume() {
        super.onResume();
        if(music_active==1) {
            MainActivity.backgroundMusic.start();
        }

    }

}
