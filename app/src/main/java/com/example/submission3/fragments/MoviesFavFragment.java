package com.example.submission3.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.submission3.Db;
import com.example.submission3.R;
import com.example.submission3.adapters.MoviesFavAdapter;
import com.example.submission3.models.MoviesData;

import java.util.ArrayList;

import static com.example.submission3.CustomApplication.db;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFavFragment extends Fragment {
    private MoviesFavAdapter moviesAdapter;
    private ProgressBar progressBarMovie;


    public MoviesFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies_fav, container, false);
        RecyclerView rvMovies = view.findViewById(R.id.rv_movies);
        progressBarMovie = view.findViewById(R.id.progressbar_movies);
        showLoading(false);
        db = Room.databaseBuilder(getContext(), Db.class, "database")
                .allowMainThreadQueries()
                .build();
        ArrayList<MoviesData> moviesData = (ArrayList<MoviesData>) db.dao().getMovie();

        rvMovies.setLayoutManager(new LinearLayoutManager(this.getContext()));
        moviesAdapter = new MoviesFavAdapter(getContext(), moviesData);
        rvMovies.setAdapter(moviesAdapter);
        return view;
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBarMovie.setVisibility(View.VISIBLE);
        } else {
            progressBarMovie.setVisibility(View.GONE);
        }
    }
}
