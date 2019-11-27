package com.example.submission3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.submission3.adapters.ViewPagerAdapter;
import com.example.submission3.fragments.MoviesFragment;
import com.example.submission3.fragments.TVShowsFragment;
import com.example.submission3.notification.MyReminderActivity;
import com.example.submission3.search.SearchMovieActivity;
import com.example.submission3.search.SearchTVActivity;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tablayout_id);
        ViewPager viewPager = findViewById(R.id.viewpager_id);

        Button btnSearchMovie = findViewById(R.id.search_movie);
        Button btnSearchTv = findViewById(R.id.search_tv);

        btnSearchMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchMovieActivity.class);
                startActivity(intent);

            }
        });

        btnSearchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchTVActivity.class);
                startActivity(intent);
            }
        });

        String movies = getResources().getString(R.string.tab_movies);
        String tvshows = getResources().getString(R.string.tab_tvshows);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new MoviesFragment(), movies);
        viewPagerAdapter.AddFragment(new TVShowsFragment(), tvshows);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.change_language_setting) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        if (menuItem.getItemId() == R.id.favorit){
            Intent intent = new Intent(this, FavoritActivity.class);
            startActivity(intent);
        }
        if (menuItem.getItemId() == R.id.reminder) {
            Intent intent = new Intent(this, MyReminderActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
