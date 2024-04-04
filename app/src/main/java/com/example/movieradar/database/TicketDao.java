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

    @Query("DELETE FROM tickets WHERE id = :ticketId")
    void deleteById(int ticketId);

    @Query("SELECT * FROM tickets")
    List<Ticket> getAllTickets();

    @Query("DELETE FROM tickets")
    void deleteAll();

    @Query("SELECT * FROM tickets WHERE date_movie >= :todayDate AND time_Movie >= :nowTime ORDER BY date_movie DESC LIMIT :topAantl")
    List<Ticket> getTopTicketsForToday(String todayDate,String nowTime, int topAantl);

    @Query("SELECT * FROM tickets WHERE date_movie >= :todayDate ORDER BY date_movie DESC LIMIT :topAantl")
    List<Ticket> getTopTicketsForToday(String todayDate, int topAantl);
}
