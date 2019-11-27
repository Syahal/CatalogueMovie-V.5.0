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
import com.example.submission3.FavoritActivity;
import com.example.submission3.R;
import com.example.submission3.details.MoviesDetail;
import com.example.submission3.models.MoviesData;

import java.util.ArrayList;

import static com.example.submission3.CustomApplication.db;

public class MoviesFavAdapter extends RecyclerView.Adapter<MoviesFavAdapter.MoviesCardViewHolder> {
    private Context context;
    private ArrayList<MoviesData> moviesData;

    public MoviesFavAdapter(Context context, ArrayList<MoviesData> moviesData) {
        this.context = context;
        this.moviesData = moviesData;
    }

    @NonNull
    @Override
    public MoviesFavAdapter.MoviesCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_movies_favolist, viewGroup, false);
        return new MoviesCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesFavAdapter.MoviesCardViewHolder moviesCardViewHolder, final int i) {
        moviesCardViewHolder.bind(moviesData.get(i));

        moviesCardViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.dao().delMovie(moviesData.get(i).getId());
                context.startActivity(new Intent(context, FavoritActivity.class));
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesData.size();
    }

    public class MoviesCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgMovie, delete;
        TextView tvMovieName;
        TextView tvMovieRelease;
        TextView tvMovieDesc;

         MoviesCardViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.img_poster_favmovie);
            tvMovieName = itemView.findViewById(R.id.tv_title_favmovie);
            tvMovieRelease = itemView.findViewById(R.id.tv_release_favmovie);
            tvMovieDesc = itemView.findViewById(R.id.tv_overview_favmovie);
            delete = itemView.findViewById(R.id.delete);

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
