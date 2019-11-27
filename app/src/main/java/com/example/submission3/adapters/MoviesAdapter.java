package com.example.submission3.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.submission3.R;
import com.example.submission3.details.MoviesDetail;
import com.example.submission3.models.MoviesData;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesCardViewHolder> {

    private ArrayList<MoviesData> moviesData = new ArrayList<>();
    private Context context;

    public MoviesAdapter(Context context) {
        this.context = context;
    }

    public MoviesAdapter(ArrayList<MoviesData> moviesData, Context context) {
        this.moviesData = moviesData;
        this.context = context;
    }

    public void setMoviesData(ArrayList<MoviesData> itemMoviesData) {
        moviesData.clear();
        moviesData.addAll(itemMoviesData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesAdapter.MoviesCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_movies_list, viewGroup, false);
        return new MoviesCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MoviesCardViewHolder moviesCardViewHolder, int i) {
        moviesCardViewHolder.bind(moviesData.get(i));
    }

    @Override
    public int getItemCount() {
        return moviesData.size();
    }

    public class MoviesCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgMovie;
        TextView tvMovieName;
        TextView tvMovieRelease;
        TextView tvMovieDesc;

         MoviesCardViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.img_poster_movie);
            tvMovieName = itemView.findViewById(R.id.tv_title_movie);
            tvMovieRelease = itemView.findViewById(R.id.tv_release_movie);
            tvMovieDesc = itemView.findViewById(R.id.tv_overview_movie);

            itemView.setOnClickListener(this);
        }

        void bind(MoviesData movData) {
             tvMovieName.setText(movData.getTitle());
             tvMovieRelease.setText(movData.getReleaseDate());
             tvMovieDesc.setText(movData.getOverview());
             Glide.with(itemView).load(movData.getPosterPath())
                     .into(imgMovie);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            MoviesData moviData = moviesData.get(position);

            moviData.setTitle(moviData.getTitle());
            moviData.setOverview(moviData.getOverview());

            Intent moveWithObjectIntent = new Intent(itemView.getContext(), MoviesDetail.class);
            moveWithObjectIntent.putExtra(MoviesDetail.EXTRA_MOVIES, moviData);
            itemView.getContext().startActivity(moveWithObjectIntent);
        }
    }
}
