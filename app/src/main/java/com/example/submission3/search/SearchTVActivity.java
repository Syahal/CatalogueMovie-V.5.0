package com.example.submission3.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.submission3.BuildConfig;
import com.example.submission3.R;
import com.example.submission3.adapters.TVShowsAdapter;
import com.example.submission3.models.TVShowsData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchTVActivity extends AppCompatActivity {

    EditText edtSearchTv;
    Button btnSearchTv;
    RecyclerView rvSearchTv;
    String tvSearch;

    TVShowsAdapter tvShowsAdapter;

    ArrayList<TVShowsData> listTVData = new ArrayList<>();
    String END_POINT_TV = "https://api.themoviedb.org/3/search/tv?api_key=" + BuildConfig.API_KEY + "&language=en-US&query=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tv);

        edtSearchTv = findViewById(R.id.edt_search_tv);
        btnSearchTv = findViewById(R.id.btn_search_tv);
        rvSearchTv = findViewById(R.id.rv_search_tv);

        btnSearchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTv();
            }
        });
    }

    private void searchTv() {
        AsyncHttpClient httpClient = new AsyncHttpClient();
        tvSearch = String.valueOf(edtSearchTv.getText());

        httpClient.get(END_POINT_TV + tvSearch, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray tvList = responseObject.getJSONArray("results");

                    for (int i = 0; i < tvList.length(); i++) {
                        JSONObject tvshows = tvList.getJSONObject(i);

                        TVShowsData tvShowsData = new TVShowsData();
                        tvShowsData.setId(tvshows.getInt("id"));
                        tvShowsData.setTitle(tvshows.getString("name"));
                        tvShowsData.setReleaseDate(tvshows.getString("first_air_date"));
                        tvShowsData.setOverview(tvshows.getString("overview"));
                        tvShowsData.setLanguage(tvshows.getString("original_language"));
                        tvShowsData.setPosterPath("https://image.tmdb.org/t/p/w185" + tvshows.getString("poster_path"));

                        listTVData.add(i, tvShowsData);
                    }

                    showTv();

                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void showTv() {
        tvShowsAdapter = new TVShowsAdapter(listTVData, getApplicationContext());
        rvSearchTv.setAdapter(tvShowsAdapter);
        rvSearchTv.setLayoutManager(new LinearLayoutManager(this));
        rvSearchTv.setHasFixedSize(true);
    }
}
