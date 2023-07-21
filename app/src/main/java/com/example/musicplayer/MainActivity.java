package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
FloatingActionButton btnPlay;
private SeekBar seekBar;
    boolean isPlaying = false;
    MediaPlayer mp = new MediaPlayer();
    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btnPlay);
        seekBar = findViewById(R.id.seekBar);

//.............................................................................

        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        String aPath = "android.resource://"+getPackageName()+"/raw/arijit_singh_song";
        Uri audioURI = Uri.parse(aPath);
        try {
            mp.setDataSource(this,audioURI);
            mp.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//...............................................................................
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    pauseAudio();
                } else {
                    playAudio();
                }
            }
        });

//....................................................................................
        seekBar.setMax(mp.getDuration());
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekBar.setProgress(mp.getCurrentPosition());
            }
        },0,100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mp.seekTo(progress);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    private  void playAudio(){
        if (!mp.isPlaying()) {
            mp.start();
            isPlaying = true;
            btnPlay.setImageResource(R.drawable.pause_music); // Set the pause button icon
        }
    }
    private void pauseAudio() {
        if (mp.isPlaying()) {
            mp.pause();
            isPlaying = false;
            btnPlay.setImageResource(R.drawable.play_music); // Set the play button icon
        }
    }
}