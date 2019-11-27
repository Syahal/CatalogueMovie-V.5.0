package com.example.submission3.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

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
import com.example.submission3.models.TVShowsData;

import org.w3c.dom.Text;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.example.submission3.CustomApplication.db;

public class TVShowsDetail extends AppCompatActivity {

    public static final String EXTRA_TVSHOWS = "extra_tvshows";

    private ArrayList<TVShowsData> listFavTVShows = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshows_detail);

        TextView tvTitle = findViewById(R.id.tv_detailtvshow_name);
        TextView tvRelease = findViewById(R.id.tv_detailtvshow_release);
        TextView tvOverview = findViewById(R.id.tv_detailtvshow_description);
        ImageView tvBlurPath = findViewById(R.id.img_tvshow_blur);
        ImageView tvPosterPath = findViewById(R.id.img_tvshow_poster);
        Button btnAdd = findViewById(R.id.buttonAdd);

        final TVShowsData tvShowsData = getIntent().getParcelableExtra(EXTRA_TVSHOWS);

        tvTitle.setText(tvShowsData.getTitle());
        tvRelease.setText(tvShowsData.getReleaseDate());
        tvOverview.setText(tvShowsData.getOverview());
        Glide.with(this).load(tvShowsData.getPosterPath())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(10, 1)))
                .into(tvBlurPath);
        Glide.with(this).load(tvShowsData.getPosterPath())
                .into(tvPosterPath);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStart();
                if (tvShowsData != null) {
                    boolean isExist = true;
                    if (listFavTVShows.size() == 0) {
                        db.dao().insertTvshowFav(tvShowsData);
                        Toast.makeText(getApplicationContext(), "TV Show berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        Log.e("top 1", "insert 1");
                    } else {
                        for (int i = 0; i < listFavTVShows.size(); i++) {
                            if (tvShowsData.getId() == listFavTVShows.get(i).getId()) {
                                Toast.makeText(getApplicationContext(), "TV Show Sudah ada", Toast.LENGTH_SHORT).show();
                                Log.e("top 2", "sudah ada");
                                isExist = false;
                            }
                        }
                        if (isExist) {
                            db.dao().insertTvshowFav(tvShowsData);
                            Toast.makeText(getApplicationContext(), "TV Show berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            Log.e("top 3", "insert 3");
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
        listFavTVShows = (ArrayList<TVShowsData>) db.dao().getTvshow();
    }
}
