package com.example.musicplayer;

import android.net.Uri;

public class MusicModel {
    String songName,singerName;
    int duration,size;
    Uri uri;
    Uri artWorkUri;
    public MusicModel(String songName,int duration,Uri uri,Uri artWorkUri,int size){
        this.songName = songName;
        //this.singerName = singerName;
        this.duration = duration;
        this.uri=uri;
        this.size=size;
        this.artWorkUri=artWorkUri;
    }
    // Getter

    public String getSongName() {
        return songName;
    }

    public String getSingerName() {
        return singerName;
    }

    public int getDuration() {
        return duration;
    }

    public Uri getUri() {
        return uri;
    }

    public Uri getArtWorkUri() {
        return artWorkUri;
    }

    public int getSize() {
        return size;
    }
}
