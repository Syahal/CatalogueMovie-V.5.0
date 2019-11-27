package com.example.submission3.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submission3.R;
import com.example.submission3.adapters.TVShowsAdapter;
import com.example.submission3.models.TVShowsData;
import com.example.submission3.viewmodels.TVShowsViewModel;

import java.util.ArrayList;

public class TVShowsFragment extends Fragment {

    private TVShowsAdapter tvShowsAdapter;
    private ProgressBar progressBarTvshow;
    private TVShowsViewModel tvShowsViewModel;

    public TVShowsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvshows, container, false);
        RecyclerView rvTvShow = view.findViewById(R.id.rv_tvshows);
        progressBarTvshow = view.findViewById(R.id.progressbar_tvshows);
        showLoading(true);
        tvShowsAdapter = new TVShowsAdapter(getContext());

        rvTvShow.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvTvShow.setAdapter(tvShowsAdapter);

        tvShowsViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TVShowsViewModel.class);
        tvShowsViewModel.getTvshows().observe(this, getTvShows);
        tvShowsViewModel.setTvShows();

        return view;
    }

    public Observer<ArrayList<TVShowsData>> getTvShows = new Observer<ArrayList<TVShowsData>>() {
        @Override
        public void onChanged(ArrayList<TVShowsData> tvShowsData) {
            if (tvShowsData != null) {
                tvShowsAdapter.setTvShowsData(tvShowsData);
            }
            showLoading(false);
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBarTvshow.setVisibility(View.VISIBLE);
        } else {
            progressBarTvshow.setVisibility(View.GONE);
        }
    }
}
