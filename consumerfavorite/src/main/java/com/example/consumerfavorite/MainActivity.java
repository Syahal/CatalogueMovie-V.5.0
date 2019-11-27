package com.example.consumerfavorite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int MOVIE = 1;
    private ConsumerFavAdapter consumerFavAdapter;
    private RecyclerView rvFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvFav = findViewById(R.id.rv_movies_fav);
        rvFav.setLayoutManager(new LinearLayoutManager(this));
        consumerFavAdapter = new ConsumerFavAdapter(this);
        rvFav.setAdapter(consumerFavAdapter);
        getSupportLoaderManager().initLoader(MOVIE, null, this);


    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == MOVIE) {
            return new CursorLoader(getApplicationContext(), Helper.URI, null, null, null, null);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == MOVIE) {
            try {
                consumerFavAdapter.setData(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (loader.getId() == MOVIE) {
            consumerFavAdapter.setData(null);
        }
    }
}
