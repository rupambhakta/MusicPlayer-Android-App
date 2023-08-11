package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class RecyclerMusicAdapter extends RecyclerView.Adapter<RecyclerMusicAdapter.ViewHolder> {
    Context context;
    ArrayList<MusicModel> arrMusic;
    public RecyclerMusicAdapter(Context context, ArrayList<MusicModel> arrMusic){
        this.context=context;
        this.arrMusic = arrMusic;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtSongName.setText(arrMusic.get(position).getSongName());
        holder.txtSingerName.setText(arrMusic.get(position).getSingerName());
        holder.txtSongDuration.setText(arrMusic.get(position).getDuration());
        //Setting Image on Music view
        Uri artWorkUri =arrMusic.get(position).artWorkUri;
        if (artWorkUri!=null){
            holder.imgViewRec.setImageURI(artWorkUri);
            if (holder.imgViewRec.getDrawable()==null){
                holder.imgViewRec.setImageResource(R.drawable.music_pic2);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, arrMusic.get(position).getSongName(), Toast.LENGTH_SHORT).show();
            }
        });
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
