package com.example.getred_y;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import me.tankery.lib.circularseekbar.CircularSeekBar;

public class MainActivity extends AppCompatActivity {

    CircularSeekBar slider_circle;
    Button redy_button;
    CountDownTimer countdown;
    Boolean counterisActive = false;
    TextView time_preset;
    MediaPlayer mediaPlayer;


    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "App is starting.", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slider_circle = findViewById(R.id.slider_circle);
        time_preset = findViewById(R.id.time_preset);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.chime);
        redy_button = findViewById(R.id.redy_button);
        slider_circle.setMax(3000);
        slider_circle.setProgress(0);

        slider_circle.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {

            @Override
                    public void onProgressChanged(@Nullable CircularSeekBar circularSeekBar, float progress, boolean b) {
                        update((int) progress);
                    }

                    @Override
                    public void onStopTrackingTouch(@Nullable CircularSeekBar circularSeekBar) {

                    }

                    @Override
                    public void onStartTrackingTouch(@Nullable CircularSeekBar circularSeekBar) {

                    }
                });
        }

    private void update(int progress) {
        int mins = progress / 60;
        int sec = progress % 60;
        String secsCount = "";
        if(sec <=9) {
            secsCount = "0" + sec;
        }
        else{
            secsCount = "" + sec;
        }
        slider_circle.setProgress(progress);
        time_preset.setText(""+ mins + ":" + secsCount);
    }

    public void timer_starts(View view) {
        if(counterisActive == false){
            counterisActive = true;
            slider_circle.setEnabled(false);
            redy_button.setText("Stop");
            countdown = new CountDownTimer((long) (slider_circle.getProgress() * 1000), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                     update((int) millisUntilFinished /1000);
                }

                @Override
                public void onFinish() {
                   reset();
                   if(mediaPlayer != null)
                       mediaPlayer.start();
                }
            }.start();
        }
        else{
            reset();
        }

    }

    private void reset() {
        time_preset.setText("00:00");
        slider_circle.setProgress(0);
        countdown.cancel();
        redy_button.setText("Red-y?");
        slider_circle.setEnabled(true);
        counterisActive = false;
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(counterisActive){
            countdown.cancel();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "App has stopped.", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "App is terminated.", Toast.LENGTH_SHORT).show();
    }


}