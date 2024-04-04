package com.example.movieradar.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movieradar.Ticket;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TicketDao {

    // Methode om een nieuwe ticket toe te voegen aan de database
    @Insert
    void insert(Ticket ticket);

    // Methode om een ticket te verwijderen uit de database
    @Delete
    void delete(Ticket ticket);

    // Query om een ticket op te halen op basis van het id
//    @Query("SELECT * FROM favo_cocktail WHERE id=:idCheck")
//    Ticket getTicketById(int idCheck);

//    @Query("SELECT * FROM favo_cocktail WHERE id=:idCheck")
//    ArrayList<Ticket> getAllTickets();
}
