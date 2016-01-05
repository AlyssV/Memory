package com.example.alyss.memory;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by jazz on 20/11/15.
 */
public class ScoreActivity extends AppCompatActivity {


    static TextView txt ;
    static String tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        txt = (TextView) findViewById(R.id.textView);


        txt.setText("Score\n" + loadSavedPreferences()+ "\n");

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

     String loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

          tmp = sharedPreferences.getString("score", ""+JeuView.score_fin);

        return tmp;

    }

}
