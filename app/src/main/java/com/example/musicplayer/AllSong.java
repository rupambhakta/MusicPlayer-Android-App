package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AllSong extends AppCompatActivity {
Intent mainActivity;
Intent playlist,favoriteActivity;
ImageView imgSongPic;
Button btnPlaylist,btnFavourite;
TextView txtSong_Name,txtSinger_Name;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_song);
        // Finding id
        imgSongPic = findViewById(R.id.imgSongPic);
        txtSong_Name = findViewById(R.id.txtSong_Name);
        txtSinger_Name = findViewById(R.id.txtSinger_Name);
        btnPlaylist = findViewById(R.id.btnPlaylist);
        btnFavourite = findViewById(R.id.btnFavorite);
        // Initialized activity
        mainActivity = new Intent(this,MainActivity.class);
        playlist = new Intent(AllSong.this,playlistActivity.class);
        favoriteActivity = new Intent(this, FavoriteActivity.class);

        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(favoriteActivity);
            }
        });
        imgSongPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivities(new Intent[]{mainActivity});
            }
        });
        txtSong_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainActivity);
            }
        });
        btnPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(playlist);
            }
        });
    }
    public void goToMainActivity(){
        mainActivity = new Intent(this, MainActivity.class);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainActivity);
        finish();
    }
}