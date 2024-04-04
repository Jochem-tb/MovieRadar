package com.example.movieradar.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.movieradar.Movie;

@Entity(tableName = "movie_list_cross_ref",
        primaryKeys = {"movieId", "listId"},
        foreignKeys = {
                @ForeignKey(entity = Movie.class,
                        parentColumns = "id",
                        childColumns = "movieId",
                        onDelete = ForeignKey.NO_ACTION),
                @ForeignKey(entity = MovieList.class,
                        parentColumns = "id",
                        childColumns = "listId",
                        onDelete = ForeignKey.NO_ACTION)
        })
public class MovieListCrossRef {
    private int movieId;
    private int listId;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }
}