package com.example.movieradar;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "tickets")
public class Ticket implements Serializable {

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
    protected Ticket(Parcel in) {
        id = in.readInt();
        titleMovie = in.readString();
        timeMovie = in.readString();
        datumMovie = in.readString();
        chairNr = in.readInt();
        rowNr = in.readInt();
    }

    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titleMovie);
        dest.writeString(timeMovie);
        dest.writeString(datumMovie);
        dest.writeInt(chairNr);
        dest.writeInt(rowNr);
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
