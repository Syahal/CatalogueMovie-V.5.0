package com.example.submission3;

import android.app.Application;

import androidx.room.Room;


public class CustomApplication extends Application {
    public static Db db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(getApplicationContext(), Db.class, "database")
                .allowMainThreadQueries()
                .build();
    }
}
