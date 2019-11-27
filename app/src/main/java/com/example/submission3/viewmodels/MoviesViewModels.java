package com.example.submission3.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submission3.models.MoviesData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MoviesViewModels extends ViewModel {

    private final MutableLiveData<ArrayList<MoviesData>> listMovies = new MutableLiveData<>();

    public void setMovies() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MoviesData> listMoviesItem = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=e513afda129f2409db6b35ab0a040237&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
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

                        listMoviesItem.add(i, moviesData);
                    }
                    listMovies.postValue(listMoviesItem);

                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Failure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<MoviesData>> getMovies() {
        return listMovies;
    }
}
