package com.example.movieradar;

public class Ticket {

    private int id;
    private final int roomNr = 1;
    private String titleMovie;
    private String timeMovie;
    private String datumMovie;
    private int chairNr;
    private int rowNr;

    public Ticket(String titleMovie, String timeMovie, String datumMovie, int chairNr, int rowNr){
        this.titleMovie = titleMovie;
        this.timeMovie = timeMovie;
        this.datumMovie = datumMovie;
        this.chairNr = chairNr;
        this.rowNr = rowNr;
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
