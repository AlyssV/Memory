package com.example.alyss.memory;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

/**
 * Created by jazz on 16/11/15.
 */
public class LaunchGame extends Activity {



    static JeuView mjeu;
    static ProgressBar progressBar;
    Timer time = new Timer();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // initialise notre activity avec le constructeur parent
        super.onCreate(savedInstanceState);
        // charge le fichier main.xml comme vue de l'activit
        setContentView(R.layout.main);


        mjeu = (JeuView)findViewById(R.id.JeuView);
        // rend visible la vue
        mjeu.setVisibility(View.VISIBLE);
        time.init();
        mjeu.setScore();
        // recuperation de la vue une voie cree  partir de son id
        progressBar = (ProgressBar) findViewById(R.id.pBAsync);


        time.setProgressBar_tmp(progressBar);

        final Button rejouerButton = (Button) findViewById(R.id.button_rejouer);
        rejouerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(JeuView.go==1) {
                    if (JeuView.gagner == 1 && Integer.parseInt(loadSavedPreferences()) < JeuView.score_fin) {
                        savePreferences("score", "" + JeuView.score_fin);

                    }
                    progressBar.setProgress(60);

                    time.init();
                    mjeu.reload();
                }

            }
        });

    }


       @Override
         protected void onPause() {
        super.onPause();
        time.block(1);
           MainActivity.backgroundMusic.pause();

           if(JeuView.gagner==1 && Integer.parseInt(loadSavedPreferences())<JeuView.score_fin){
               savePreferences("score", "" + JeuView.score_fin);

           }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (time.getBlock_tmp() == 1) {
            time.block(0);
            time.launch();
        }
        if(SystemeActivity.music_active==1) {
            MainActivity.backgroundMusic.start();
        }

    }

    void savePreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    String loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

       String tmp = sharedPreferences.getString("score", "" + JeuView.score_fin);

        return tmp;

    }

}
