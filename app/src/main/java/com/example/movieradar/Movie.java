package com.example.movieradar;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.example.movieradar.database.DateConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Movie implements Serializable {
    public static final String SHARE_KEY = "MovieKey";
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String imdb_id;
    private String key;
    private Boolean adult;
    private String backdrop_path;
    @JsonProperty("budget")
    private Long budget;
    @Ignore
    private ArrayList<Genre> genres;
    private String original_language;
    private String overview;
    private Double popularity;
    private String poster_path;
@TypeConverters(DateConverter.class)
    private Date release_date;
    @JsonProperty("revenue")
    private Long revenue;
    @JsonProperty("runtime")
    private Integer runtime;
    private String status;
    private String title;
    private Boolean video;
    private Float vote_average;
    private Integer vote_count;

    public static String getShareKey() {
        return SHARE_KEY;
    }

    public Movie() {}


    public Movie(int id, String imdb_id, Boolean adult, String backdrop_path, ArrayList<Genre> genres, String original_language, String overview, Double popularity, String poster_path, Date release_date, Long revenue, int runtime, String status, String title, Boolean video, float vote_average, int vote_count) {
        this.id = id;
        this.imdb_id = imdb_id;
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.genres = genres;
        this.original_language = original_language;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.revenue = revenue;
        this.runtime = runtime;
        this.status = status;
        this.title = title;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", imdb_id='" + imdb_id + '\'' +
                ", key='" + key + '\'' +
                ", adult=" + adult +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", budget=" + budget +
                ", genres=" + genres +
                ", original_language='" + original_language + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", poster_path='" + poster_path + '\'' +
                ", release_date=" + release_date +
                ", revenue=" + revenue +
                ", runtime=" + runtime +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", video=" + video +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                '}';
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public Long getRevenue() {
        return revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Float getVote_average() {
        return vote_average;
    }

    public void setVote_average(Float vote_average) {
        this.vote_average = vote_average;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }


}
