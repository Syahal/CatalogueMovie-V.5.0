package com.example.consumerfavorite;

import android.net.Uri;

public class Helper {

    private static final String TABLE_NAME = "MoviesData";
    private static final String AUTHORITY = "com.example.submission3";
    private static final String SCHEME = "content";

    static final Uri URI = new Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    static final String TITLE = "title";
    static final String RELEASE_DATE = "releaseDate";
    static final String OVERVIEW = "overview";
    static final String POSTER_PATH = "posterPath";
}
