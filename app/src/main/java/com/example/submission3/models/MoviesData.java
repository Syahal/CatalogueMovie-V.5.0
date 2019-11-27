package com.example.submission3.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONObject;
@Entity
public class MoviesData implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int key;
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "posterPath")
    private String posterPath;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "releaseDate")
    private String releaseDate;
    @ColumnInfo(name = "language")
    private String language;

    public MoviesData(Parcel in) {
        key = in.readInt();
        id = in.readInt();
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        language = in.readString();
    }

    public MoviesData() {
    }

    public static final Creator<MoviesData> CREATOR = new Creator<MoviesData>() {
        @Override
        public MoviesData createFromParcel(Parcel in) {
            return new MoviesData(in);
        }

        @Override
        public MoviesData[] newArray(int size) {
            return new MoviesData[size];
        }
    };

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(key);
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeString(language);
    }
}
