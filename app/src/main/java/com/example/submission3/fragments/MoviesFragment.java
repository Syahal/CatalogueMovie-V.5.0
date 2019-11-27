package com.example.submission3.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.submission3.R;
import com.example.submission3.adapters.MoviesAdapter;
import com.example.submission3.models.MoviesData;
import com.example.submission3.viewmodels.MoviesViewModels;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBarMovie;
    private MoviesViewModels moviesViewModels;

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        RecyclerView rvMovies = view.findViewById(R.id.rv_movies);
        progressBarMovie = view.findViewById(R.id.progressbar_movies);
        showLoading(true);
        moviesAdapter = new MoviesAdapter(getContext());

        rvMovies.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvMovies.setAdapter(moviesAdapter);

        moviesViewModels = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MoviesViewModels.class);
        moviesViewModels.getMovies().observe(this, getMovies);
        moviesViewModels.setMovies();

        return view;
    }

    public Observer<ArrayList<MoviesData>> getMovies = new Observer<ArrayList<MoviesData>>() {
        @Override
        public void onChanged(ArrayList<MoviesData> moviesData) {
            if (moviesData != null) {
                moviesAdapter.setMoviesData(moviesData);
            }
            showLoading(false);
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBarMovie.setVisibility(View.VISIBLE);
        } else {
            progressBarMovie.setVisibility(View.GONE);
        }
    }
}
