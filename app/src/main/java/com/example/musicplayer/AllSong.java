package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllSong extends AppCompatActivity {
    ArrayList<MusicModel> arrMusic = new ArrayList<>();
Intent mainActivity;
Intent playlist,favoriteActivity;
ImageView imgSongPic;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrMusic.add(new MusicModel("ami ja tomar","Arijit Singh","5.22"));
        arrMusic.add(new MusicModel("Ka tumi nondini","Shaan ","04.29"));
        arrMusic.add(new MusicModel("Ka tumi nondini","Shaan ","04.29"));
        arrMusic.add(new MusicModel("Ka  nondini","Shaan ","23.29"));
        arrMusic.add(new MusicModel("Ka tumi ","Rupam ","0.29"));
        arrMusic.add(new MusicModel(" tumi nondini","Safan ","9.29"));
        arrMusic.add(new MusicModel("Ka tumi nondini","bhakta ","04.29"));
        arrMusic.add(new MusicModel(" nondini","Shaan ","04.29"));
        arrMusic.add(new MusicModel("ami ja tomar","Arijit Singh","5.22"));
        arrMusic.add(new MusicModel("Ka tumi nondini","Shaan ","04.29"));
        arrMusic.add(new MusicModel("Ka tumi nondini","Shaan ","04.29"));
        arrMusic.add(new MusicModel("Ka  nondini","Shaan ","23.29"));
        arrMusic.add(new MusicModel("Ka tumi ","Rupam ","0.29"));
        arrMusic.add(new MusicModel(" tumi nondini","Safan ","9.29"));
        arrMusic.add(new MusicModel("Ka tumi nondini","bhakta ","04.29"));
        arrMusic.add(new MusicModel(" nondini","Shaan ","04.29"));
        arrMusic.add(new MusicModel("ami ja tomar","Arijit Singh","5.22"));
        arrMusic.add(new MusicModel("Ka tumi nondini","Shaan ","04.29"));
        arrMusic.add(new MusicModel("Ka tumi nondini","Shaan ","04.29"));
        arrMusic.add(new MusicModel("Ka  nondini","Shaan ","23.29"));
        arrMusic.add(new MusicModel("Ka tumi ","Rupam ","0.29"));
        arrMusic.add(new MusicModel(" tumi nondini","Safan ","9.29"));
        RecyclerMusicAdapter adapter = new RecyclerMusicAdapter(this,arrMusic);
        recyclerView.setAdapter(adapter);
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
            }else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        }
    }
//    private ArrayList<Music> getAllAudio(){
//        ArrayList<Music> tempList = new ArrayList<>();
//        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
//        String[] projection = {MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.DATE_ADDED ,MediaStore.Audio.Media.DATA};
//        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                projection,
//                selection,
//                null,
//                MediaStore.Audio.Media.DATE_ADDED+" DESC",
//                null);
//        if (cursor!=null){
//            if (cursor.moveToFirst()){
//                do{
//                    @SuppressLint("Range") String titleC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
//                    @SuppressLint("Range") String idC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
//                    @SuppressLint("Range") String albumC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
//                    @SuppressLint("Range") String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
//                    @SuppressLint("Range") String pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
//                    @SuppressLint("Range") long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
//
//                }while (cursor.moveToNext());
//                cursor.close();
//            }
//        }
//        return tempList;
//    }
}