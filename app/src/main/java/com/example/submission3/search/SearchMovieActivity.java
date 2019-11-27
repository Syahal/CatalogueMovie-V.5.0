package com.example.submission3.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.submission3.BuildConfig;
import com.example.submission3.R;
import com.example.submission3.adapters.MoviesAdapter;
import com.example.submission3.models.MoviesData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchMovieActivity extends AppCompatActivity {

    EditText edtSearchMovie;
    Button btnSearchMovie;
    RecyclerView rvSearchMovie;
    String movieSearch;

    MoviesAdapter moviesAdapter;

    ArrayList<MoviesData> listMoviesData = new ArrayList<>();
    String END_POINT_MOVIE = "https://api.themoviedb.org/3/search/movie?api_key=" + BuildConfig.API_KEY + "&language=en-US&query=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        edtSearchMovie = findViewById(R.id.edt_search_movie);
        btnSearchMovie = findViewById(R.id.btn_search_movie);
        rvSearchMovie = findViewById(R.id.rv_search_movie);

        btnSearchMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMovie();
            }
        });
    }

    private void searchMovie() {
        AsyncHttpClient httpClient = new AsyncHttpClient();
        movieSearch = String.valueOf(edtSearchMovie.getText());

        httpClient.get(END_POINT_MOVIE + movieSearch, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray moviesList = responseObject.getJSONArray("results");

                    for (int i = 0; i < moviesList.length(); i++) {
                        JSONObject movies = moviesList.getJSONObject(i);

                        MoviesData moviesData = new MoviesData();
                        moviesData.setId(movies.getInt("id"));
                        moviesData.setTitle(movies.getString("title"));
                        moviesData.setReleaseDate(movies.getString("release_date"));
                        moviesData.setOverview(movies.getString("overview"));
                        moviesData.setLanguage(movies.getString("original_language"));
                        moviesData.setPosterPath("https://image.tmdb.org/t/p/w185" + movies.getString("poster_path"));

                        listMoviesData.add(i, moviesData);
                    }

                    showMovie();

                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void showMovie() {
        moviesAdapter = new MoviesAdapter(listMoviesData, getApplicationContext());
        rvSearchMovie.setAdapter(moviesAdapter);
        rvSearchMovie.setLayoutManager(new LinearLayoutManager(this));
        rvSearchMovie.setHasFixedSize(true);
    }
}
