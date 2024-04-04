package com.example.movieradar.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movieradar.Ticket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Ticket.class}, version = 1, exportSchema = false)
public abstract class TicketDatabase extends RoomDatabase {
    private static final String LOG_TAG = "TicketDatabase";

    private static TicketDatabase INSTANCE;
    public abstract TicketDao ticketDao();

    // The number of threads used for database operations
    private static final int NUMBER_OF_THREADS = 2;

    // ExecutorService for executing database operations on background threads
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Method to obtain an instance of the database
    public static TicketDatabase getDatabase(final Context context) {
        // Check if an instance of the database already exists, otherwise create a new one
        if (INSTANCE == null) {
            synchronized (TicketDatabase.class) {
                if (INSTANCE == null) {
                    Log.d(LOG_TAG, "Creating new database instance");
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TicketDatabase.class, "ticket_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
