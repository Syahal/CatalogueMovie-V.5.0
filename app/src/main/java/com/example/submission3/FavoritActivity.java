package com.example.submission3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.submission3.adapters.ViewPagerAdapter;
import com.example.submission3.fragments.MoviesFavFragment;
import com.example.submission3.fragments.TVShowsFavragment;
import com.google.android.material.tabs.TabLayout;

public class FavoritActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit);

        TabLayout tabLayout = findViewById(R.id.tablayout_id);
        ViewPager viewPager = findViewById(R.id.viewpager_id);

        String movies = getResources().getString(R.string.tab_movies);
        String tvshows = getResources().getString(R.string.tab_tvshows);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new MoviesFavFragment(), movies);
        viewPagerAdapter.AddFragment(new TVShowsFavragment(), tvshows);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
