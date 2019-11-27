package com.example.submission3;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.submission3.models.MoviesData;
import com.example.submission3.models.TVShowsData;

import java.util.List;

/**
 * Dibuat oleh petersam
 */
@Dao
public interface DAO {
    @Insert void insertMovieFav(MoviesData movie);
    @Query("SELECT * FROM MoviesData") List<MoviesData> getMovie();
    @Query("DELETE FROM MoviesData WHERE id LIKE :id") void delMovie(int id);
    @Query("SELECT * FROM MoviesData") Cursor selectAll();
    @Query("SELECT * FROM MoviesData WHERE id = :id") Cursor selectById(long id);

    @Insert
    void insertTvshowFav(TVShowsData tvshow);
    @Query("SELECT * FROM TVShowsData") List<TVShowsData> getTvshow();
    @Query("DELETE FROM TVShowsData WHERE id LIKE :id") void delTvshowFav(int id);
}
