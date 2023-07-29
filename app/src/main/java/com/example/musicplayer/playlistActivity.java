package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class playlistActivity extends AppCompatActivity {
ImageButton btnBack;
Intent allSongActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        btnBack = findViewById(R.id.btnBack);
        allSongActivity = new Intent(this, AllSong.class);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(allSongActivity);
            }
        });
    }
}