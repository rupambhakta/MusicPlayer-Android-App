package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllSong extends AppCompatActivity {
    ArrayList<MusicModel> allSong = new ArrayList<>();
Intent mainActivity;
Intent playlist,favoriteActivity;
ImageView imgSongPic;
RecyclerMusicAdapter adapter;

RecyclerView recyclerView;
Button btnPlaylist,btnFavourite;
TextView txtSong_Name,txtSinger_Name;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_song);
        requestRunTimePermission();
        // Finding id
        imgSongPic = findViewById(R.id.imgSongPic);
        txtSong_Name = findViewById(R.id.txtSong_Name);
        txtSinger_Name = findViewById(R.id.txtSinger_Name);
        btnPlaylist = findViewById(R.id.btnPlaylist);
        btnFavourite = findViewById(R.id.btnFavorite);
        recyclerView = findViewById(R.id.recView);
        // Initialized activity
        mainActivity = new Intent(this,MainActivity.class);
        playlist = new Intent(AllSong.this,playlistActivity.class);
        favoriteActivity = new Intent(this, FavoriteActivity.class);
        // Recycler view initialization
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        RecyclerMusicAdapter adapter = new RecyclerMusicAdapter(this,arrMusic);
//        recyclerView.setAdapter(adapter);
        //..................................................
        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(favoriteActivity);
            }
        });
        imgSongPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainActivity);
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
    private void requestRunTimePermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                fetchSong();
            }else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        }
    }

    private void fetchSong() {
        // Define a list to carry sing
        ArrayList<MusicModel> songs= new ArrayList<>();
        Uri mediaStoreUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            mediaStoreUri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        }else {
            mediaStoreUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
        //Define projection
        String[] projection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.SIZE
                //MediaStore.Audio.Media.ALBUM_ARTIST,
        };
        // Order
        String sortOrder = MediaStore.Audio.Media.DATE_ADDED+"DESC";
        // Get the song
        try (Cursor cursor = getContentResolver().query(mediaStoreUri,projection,null,null,sortOrder)){
            // cache cursor indices
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
            int albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);
                    //int album_Artist_Column = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ARTIST);
            // clear the previous loaded before adding loading again
            while(cursor.moveToNext()){
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                long albumId = cursor.getLong(albumIdColumn);
                int size = cursor.getInt(sizeColumn);
                        //String artistNameColumn = cursor.getString(album_Artist_Column);

                // Song uri
                Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,id);

                // album artwork uri
                Uri albumArtWorkUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);

                //remove .mp3 extension from the sing name
                name = name.substring(0,name.lastIndexOf("."));

                // Song Item
                MusicModel song = new MusicModel(name,duration,uri,albumArtWorkUri,size);

                //Add song item to song list
                songs.add(song);
            }
            // Display Songs
            showSongs(songs);
        }
    }

    private void showSongs(ArrayList<MusicModel> songs) {
        if (songs.size()==0){
            Toast.makeText(this, "No song present.", Toast.LENGTH_SHORT).show();
            return;
        }
        // Save song
        allSong.clear();
        allSong.addAll(songs);

        //Layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //songs adapter
        adapter = new RecyclerMusicAdapter(this,songs);

        // set adapter
        recyclerView.setAdapter(adapter);
    }


}