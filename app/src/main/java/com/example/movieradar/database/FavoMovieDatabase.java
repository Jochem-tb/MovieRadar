package com.example.movieradar.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movieradar.Movie;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class FavoMovieDatabase extends RoomDatabase {
    private static final String LOG_TAG = "FavoMovieDatabase";

    private static FavoMovieDatabase INSTANCE;
    public abstract FavoMovieDao favoMovieDao();

    // The number of threads used for database operations
    private static final int NUMBER_OF_THREADS = 2;

    // ExecutorService for executing database operations on background threads
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Method to obtain an instance of the database
    public static FavoMovieDatabase getDatabase(final Context context) {
        // Check if an instance of the database already exists, otherwise create a new one
        if (INSTANCE == null) {
            synchronized (FavoMovieDatabase.class) {
                if (INSTANCE == null) {
                    Log.d(LOG_TAG, "Creating new database instance");
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    FavoMovieDatabase.class, "favoMovieDatabase")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

