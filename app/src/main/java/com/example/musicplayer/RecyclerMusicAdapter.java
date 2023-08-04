package com.example.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class RecyclerMusicAdapter extends RecyclerView.Adapter<RecyclerMusicAdapter.ViewHolder> {
    Context context;
    ArrayList<MusicModel> arrMusic;
    RecyclerMusicAdapter(Context context, ArrayList<MusicModel> arrMusic){
        this.context=context;
        this.arrMusic = arrMusic;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtSongName.setText(arrMusic.get(position).songName);
        holder.txtSingerName.setText(arrMusic.get(position).singerName);
        holder.txtSongDuration.setText(arrMusic.get(position).duration);
    }

    @Override
    public int getItemCount() {
        return arrMusic.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSongName,txtSingerName,txtSongDuration;
        ShapeableImageView imgViewRec;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtSongName = itemView.findViewById(R.id.txtSongNameRec);
            txtSingerName = itemView.findViewById(R.id.txtSingerNameRec);
            txtSongDuration = itemView.findViewById(R.id.txtSongDurationRec);
            imgViewRec = itemView.findViewById(R.id.imgViewRec);
        }
    }
}
