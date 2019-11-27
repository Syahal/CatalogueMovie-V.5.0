package com.example.submission3;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.submission3.models.MoviesData;
import com.example.submission3.models.TVShowsData;

@Database(entities = {MoviesData.class, TVShowsData.class}, version = 1, exportSchema = false)
public abstract class Db extends RoomDatabase {
    public abstract DAO dao();
}
