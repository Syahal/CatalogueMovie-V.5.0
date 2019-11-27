package com.example.submission3.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;


import com.example.submission3.DAO;
import com.example.submission3.Db;

import java.util.Objects;

public class MyContentProvider extends ContentProvider {

    private Db dbFavorite;
    private DAO dao;

    private static final String DATABASE_NAME = "database";
    private static final String TABLE_NAME = "MoviesData";
    private static final String AUTHORITY = "com.example.submission3";
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int FAV_1 = 1;
    private static final int FAV_2 = 2;

    static {
        URI_MATCHER.addURI(AUTHORITY, TABLE_NAME, FAV_1);
        URI_MATCHER.addURI(AUTHORITY, TABLE_NAME + "/#", FAV_2);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        return null;
    }

    @Override
    public boolean onCreate() {
        dbFavorite = Room.databaseBuilder(Objects.requireNonNull(getContext()), Db.class, DATABASE_NAME).build();
        dao = dbFavorite.dao();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final int code = URI_MATCHER.match(uri);
        if (code == FAV_1 || code == FAV_2) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }

            final Cursor cursor;
            if (code == FAV_1) {
                cursor = dao.selectAll();
            } else {
                cursor = dao.selectById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown Uri : " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        return 0;
    }
}
