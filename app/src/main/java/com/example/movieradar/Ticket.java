package com.example.movieradar;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "tickets")
public class Ticket {

    @Ignore
    public static final String SHARE_KEY = "TicketKey";
    @Ignore
    private final int roomNr = 1;
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "user_id")
    private int userId;
    @ColumnInfo(name = "title_Movie")
    private String titleMovie;
    @ColumnInfo(name = "time_Movie")
    private String timeMovie;
    @ColumnInfo(name = "date_movie")
    private String datumMovie;
    @ColumnInfo(name = "seat_nr")
    private int chairNr;
    @ColumnInfo(name = "row_nr")
    private int rowNr;

    public Ticket(String titleMovie, String timeMovie, String datumMovie, int chairNr, int rowNr){
        this.titleMovie = titleMovie;
        this.timeMovie = timeMovie;
        this.datumMovie = datumMovie;
        this.chairNr = chairNr;
        this.rowNr = rowNr;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static String getShareKey() {
        return SHARE_KEY;
    }

    public int getRowNr() {
        return rowNr;
    }

    public void setRowNr(int rowNr) {
        this.rowNr = rowNr;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomNr() {
        return roomNr;
    }

    public String getTitleMovie() {
        return titleMovie;
    }

    public void setTitleMovie(String titleMovie) {
        this.titleMovie = titleMovie;
    }

    public String getTimeMovie() {
        return timeMovie;
    }

    public void setTimeMovie(String timeMovie) {
        this.timeMovie = timeMovie;
    }

    public String getDatumMovie() {
        return datumMovie;
    }

    public void setDatumMovie(String datumMovie) {
        this.datumMovie = datumMovie;
    }

    public int getChairNr() {
        return chairNr;
    }

    public void setChairNr(int chairNr) {
        this.chairNr = chairNr;
    }
}
