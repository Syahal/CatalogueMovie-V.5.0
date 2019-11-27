package com.example.submission3.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submission3.models.TVShowsData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TVShowsViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<TVShowsData>> listTvShows = new MutableLiveData<>();

    public void setTvShows() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TVShowsData> listTvShowsItem = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=e513afda129f2409db6b35ab0a040237&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray tvshowsList = responseObject.getJSONArray("results");

                    for (int i = 0; i < tvshowsList.length(); i++) {
                        JSONObject tvshows = tvshowsList.getJSONObject(i);

                        TVShowsData tvShowsData = new TVShowsData();
                        tvShowsData.setId(tvshows.getInt("id"));
                        tvShowsData.setTitle(tvshows.getString("name"));
                        tvShowsData.setReleaseDate(tvshows.getString("first_air_date"));
                        tvShowsData.setOverview(tvshows.getString("overview"));
                        tvShowsData.setLanguage(tvshows.getString("original_language"));
                        tvShowsData.setPosterPath("https://image.tmdb.org/t/p/w185" + tvshows.getString("poster_path"));

                        listTvShowsItem.add(i, tvShowsData);
                    }
                    listTvShows.postValue(listTvShowsItem);

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

    public LiveData<ArrayList<TVShowsData>> getTvshows() {
        return listTvShows;
    }
}
