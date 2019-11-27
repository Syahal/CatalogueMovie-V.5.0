package com.example.submission3.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.submission3.Db;
import com.example.submission3.MainActivity;
import com.example.submission3.R;
import com.example.submission3.models.MoviesData;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.example.submission3.CustomApplication.db;

public class MoviesDetail extends AppCompatActivity {

    public static final String EXTRA_MOVIES = "extra_movies";

    private ArrayList<MoviesData> listFavMovie = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);

        TextView movTitle = findViewById(R.id.tv_detailmovie_name);
        TextView movRelease = findViewById(R.id.tv_detailmovie_release);
        TextView movOverview = findViewById(R.id.tv_detailmovie_description);
        ImageView movBlurPath = findViewById(R.id.img_movie_blur);
        ImageView movPosterPath = findViewById(R.id.img_movie_poster);
        Button btnAdd = findViewById(R.id.buttonAdd);

        final MoviesData moviesData = getIntent().getParcelableExtra(EXTRA_MOVIES);

        movTitle.setText(moviesData.getTitle());
        movRelease.setText(moviesData.getReleaseDate());
        movOverview.setText(moviesData.getOverview());
        Glide.with(this).load(moviesData.getPosterPath())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(10, 1)))
                .into(movBlurPath);
        Glide.with(this).load(moviesData.getPosterPath())
                .into(movPosterPath);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();
                if (moviesData != null) {
                    boolean isExist = true;
                    if (listFavMovie.size() == 0) {
                        db.dao().insertMovieFav(moviesData);
                        sendBroadcast(new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE));
                        Toast.makeText(getApplicationContext(), "Movies berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < listFavMovie.size(); i++) {
                            if (moviesData.getId() == listFavMovie.get(i).getId()) {
                                Toast.makeText(getApplicationContext(), "Movies telah ditambahkan", Toast.LENGTH_SHORT).show();
                                isExist = false;
                            }
                        }
                        if (isExist) {
                            db.dao().insertMovieFav(moviesData);
                            sendBroadcast(new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE));
                            Toast.makeText(getApplicationContext(), "Movies berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = Room.databaseBuilder(this, Db.class, "database")
                .allowMainThreadQueries()
                .build();
        listFavMovie = (ArrayList<MoviesData>) db.dao().getMovie();
    }
}
