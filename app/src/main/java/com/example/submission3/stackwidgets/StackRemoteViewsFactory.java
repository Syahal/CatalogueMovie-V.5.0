package com.example.submission3.stackwidgets;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bumptech.glide.Glide;
import com.example.submission3.Db;
import com.example.submission3.R;
import com.example.submission3.models.MoviesData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.submission3.CustomApplication.db;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<Bitmap> widgetItems = new ArrayList<>();
    private final Context context;

    StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        db = Room.databaseBuilder(context, Db.class, "database")
                .allowMainThreadQueries()
                .build();
        ArrayList<MoviesData> moviesData = (ArrayList<MoviesData>) db.dao().getMovie();

        for (int i = 0; i < moviesData.size(); i++) {
            try {
                URL url = new URL(moviesData.get(i).getPosterPath());
                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                widgetItems.add(bitmap);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return widgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_widget);
        remoteViews.setImageViewBitmap(R.id.image_widget, widgetItems.get(position));

        Bundle bundle = new Bundle();
        bundle.putInt(FavoriteWidget.EXTRA_WIDGET, position);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        remoteViews.setOnClickFillInIntent(R.id.image_widget, intent);
        return remoteViews;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
