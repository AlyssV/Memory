package com.example.alyss.memory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by jazz on 16/11/15.
 */

public class Timer  implements Runnable {


    static ProgressBar progressBar_tmp;
    static int progressStatus = 60;
    static int return_stat = 60;
    static int block_tmp = 0;

/**
     * Called when the activity is first created.
  */

public void setProgressBar_tmp(ProgressBar bar_tmp){
    progressBar_tmp=bar_tmp;
}

    public void init(){
        progressStatus = 60;
        return_stat=60;
        block_tmp=0;

    }


    public void launch() {
        // startProgress();

        new Thread(new Timer()).start();
    }


    public void destroy(){

    }

    public void run() {

        while (progressStatus > 0) {

            // Update the progress bar and display the current value in the text view


            //textView.setText(progressStatus+"/"+progressBar.getMax());


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (block_tmp == 0) {

                progressStatus -= 1;
                status_time_tempo(progressStatus);
            }else if(block_tmp==1){
                break;

            }
            progressBar_tmp.setProgress(progressStatus);

        }
    }


    public void status_time_tempo(int tmp) {
        return_stat = progressStatus;
    }


    public int status_time() {
        return return_stat;
    }

    int getBlock_tmp(){
        return block_tmp;
    }

    public int block(int block) {


        block_tmp = block;

        return block_tmp;
    }


}
