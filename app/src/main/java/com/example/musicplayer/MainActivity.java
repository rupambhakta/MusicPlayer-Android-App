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
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
FloatingActionButton btnPlay,btnPlayPrevious,btnPlayNext;
TextView textViewCurrentTime,textViewTotalTime,txtSongName;
private List<Uri> songUris;
private int currentIndex = 0;
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
        textViewCurrentTime = findViewById(R.id.txtCountStart);
        textViewTotalTime = findViewById(R.id.txtSongDuration);
        btnPlayPrevious = findViewById(R.id.btnPlayPrevious);
        btnPlayNext = findViewById(R.id.btnPlayNext);
        txtSongName = findViewById(R.id.txtSongTitle);
//.............................................................................
        // Songs Uri...
        String song1 = "android.resource://"+getPackageName()+"/raw/arijit_singh_song";
        String song2 = "android.resource://"+getPackageName()+"/raw/rabindra_sangit1";
        String song3 = "android.resource://"+getPackageName()+"/raw/rabindra_sangit2";
        String song4 = "android.resource://"+getPackageName()+"/raw/rabindra_sangit3";
        //..................................................................
        songUris = new ArrayList<>();
        songUris.add(Uri.parse(song1));
        songUris.add(Uri.parse(song2));
        songUris.add(Uri.parse(song3));
        songUris.add(Uri.parse(song4));
        //...........................................................................
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        Uri audioURI = Uri.parse(String.valueOf(songUris.get(currentIndex)));
//        Uri audioURI = Uri.parse(song2);
        try {
            mp.setDataSource(this,songUris.get(currentIndex));
            mp.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//...............................................................................
        songName();
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    pauseAudio();
                } else {
                    playAudio();
                }
                seekMax();
            }
        });

        btnPlayNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp!=null){
                    btnPlay.setImageResource(R.drawable.pause_music);
                }
                if (currentIndex<songUris.size()-1){
                    currentIndex++;
                }else {
                    currentIndex=0;
                }
                assert mp != null;
                if (mp.isPlaying()){
                    mp.stop();
                }
                mp = MediaPlayer.create(getApplicationContext(),songUris.get(currentIndex));
                mp.start();
                songName();
                seekMax();
            }
        });
        btnPlayPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp!=null){
                    btnPlay.setImageResource(R.drawable.pause_music);
                }
                if (currentIndex>0){
                    currentIndex--;
                }else {
                    currentIndex = songUris.size()-1;
                }
                assert mp != null;
                if (mp.isPlaying()){
                    mp.stop();
                }
                mp = MediaPlayer.create(getApplicationContext(),songUris.get(currentIndex));
                mp.start();
                songName();
                seekMax();
            }

        });
//....................................................................................
        // Setting song duration
        int totalDuration = mp.getDuration();
        textViewTotalTime.setText(formatTime(totalDuration));
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
                textViewCurrentTime.setText(formatTime(mp.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    private void playNextSong() {
        // Called when a song finishes playing

        // Increment the index to play the next song
        currentIndex++;

        // Check if there are more songs to play
        if (currentIndex < songUris.size()) {
            // Release the resources of the current media player
            mp.release();

            // Create a new media player and play the next song
            mp = new MediaPlayer();
            try {
                mp.setDataSource(this, songUris.get(currentIndex));
                mp.prepare();
                mp.start();

                // Set the OnCompletionListener again for the new media player
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        playNextSong();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // All songs have been played, you can handle this situation as per your app logic
            // For example, you can loop the playlist or stop the playback.
            // Here, I am stopping the playback.
            mp.release();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the resources of the media player when the activity is destroyed
        if (mp != null) {
            mp.release();
            mp = null;
        }
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
    private String formatTime(int milliseconds) {
        int seconds = milliseconds / 1000;
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, remainingSeconds);
    }
    private void songName(){
        if (currentIndex==0){
            txtSongName.setText("Qaafirana : Arijit Singh");
        }
        if (currentIndex==1){
            txtSongName.setText("Nittyo Tomar Je Phool Phote : Shaan");
        }
        if (currentIndex==2){
            txtSongName.setText("O Jonaki, Ki Shukhe : Shaan");
        }
        if (currentIndex==3){
            txtSongName.setText("Pran Chay Chokhhu Na Chay : Shaan");
        }
    }
    private void seekMax(){
        // Setting song duration
        int totalDuration = mp.getDuration();
        textViewTotalTime.setText(formatTime(totalDuration));
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
                textViewCurrentTime.setText(formatTime(mp.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}