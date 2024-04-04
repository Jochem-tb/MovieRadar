package com.example.movieradar.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movieradar.Movie;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ListItemDao {

    // Methode om een nieuwe ticket toe te voegen aan de database
    @Insert
    void insert(Movie movie, MovieList movieList);

    // Methode om een ticket te verwijderen uit de database
    @Delete
    void delete(Movie movie, MovieList movieList);

    // Query om een movies op te halen op basis van het listId
//    @Query("SELECT * FROM favo_cocktail WHERE id=:idCheck")
//    ArrayList<Movie> getMoviesByListId(int idCheck);
}
