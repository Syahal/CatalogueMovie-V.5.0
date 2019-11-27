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
import com.example.submission3.adapters.TVShowsAdapter;
import com.example.submission3.adapters.TVShowsFavAdapter;
import com.example.submission3.models.TVShowsData;

import java.util.ArrayList;

import static com.example.submission3.CustomApplication.db;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowsFavragment extends Fragment {
    private TVShowsFavAdapter tvShowsAdapter;
    private ProgressBar progressBar;

    public TVShowsFavragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tvshows_favragment, container, false);
        RecyclerView rvTVShow = view.findViewById(R.id.rv_tvshows);
        progressBar = view.findViewById(R.id.progressbar_tvshows);
        showLoading(false);
        db = Room.databaseBuilder(getContext(), Db.class, "database")
                .allowMainThreadQueries()
                .build();
        ArrayList<TVShowsData> tvshowsData = (ArrayList<TVShowsData>) db.dao().getTvshow();

        rvTVShow.setLayoutManager(new LinearLayoutManager(this.getContext()));
        tvShowsAdapter = new TVShowsFavAdapter(getContext(), tvshowsData);
        rvTVShow.setAdapter(tvShowsAdapter);
        return view;
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
