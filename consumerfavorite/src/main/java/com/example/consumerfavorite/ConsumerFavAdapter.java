package com.example.consumerfavorite;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class ConsumerFavAdapter extends RecyclerView.Adapter<ConsumerFavAdapter.ConsumerViewHolder> {

    private Cursor cursor;
    private Context context;

    ConsumerFavAdapter (Context context) {
        this.context = context;
    }

    void setData(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ConsumerFavAdapter.ConsumerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list, parent, false);
        return new ConsumerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsumerFavAdapter.ConsumerViewHolder holder, int position) {
        holder.bind(cursor.moveToPosition(position));
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    public class ConsumerViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMovie;
        TextView tvMovieName, tvMovieRelease, tvMovieDesc;

        public ConsumerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.img_poster_movie);
            tvMovieName = itemView.findViewById(R.id.tv_title_movie);
            tvMovieRelease = itemView.findViewById(R.id.tv_release_movie);
            tvMovieDesc = itemView.findViewById(R.id.tv_overview_movie);
        }

        void bind(boolean favData) {
            if (favData) {
                tvMovieName.setText(cursor.getString(cursor.getColumnIndexOrThrow(Helper.TITLE)));
                tvMovieRelease.setText(cursor.getString(cursor.getColumnIndexOrThrow(Helper.RELEASE_DATE)));
                tvMovieDesc.setText(cursor.getString(cursor.getColumnIndexOrThrow(Helper.OVERVIEW)));
                Glide.with(context).load(cursor.getString(cursor.getColumnIndexOrThrow(Helper.POSTER_PATH)))
                        .into(imgMovie);
            }
        }
    }
}
