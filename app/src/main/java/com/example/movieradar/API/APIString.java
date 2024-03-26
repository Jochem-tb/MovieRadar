package com.example.movieradar.API;

import android.util.Log;

public class APIString {
    private final String LOG_TAG = "APIString";
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String API_KEY = "731b0900535ff5476ae98c326ef7413c";
    private static final String POPULAR_URL_COMPLETE = "https://api.themoviedb.org/3/trending/movie/day?language=en-US&api_key=731b0900535ff5476ae98c326ef7413c";
    private static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/w780/";

    private StringBuilder mBuilder;
    private boolean isFiltered = false;
    private boolean isSorted = false;

    public APIString() {
        mBuilder = new StringBuilder(BASE_URL);
    }

    public void search(String query) {
        appendSeparator();
        mBuilder.append("/search/movie");
        addQueryParam("query", query);
    }

    public void isAdult(boolean isAdult) {
        addQueryParam("include_adult", String.valueOf(isAdult));
    }

    public void setPage(int page) {
        addQueryParam("page", String.valueOf(page));
    }

    public void finish() {
        addQueryParam("api_key", API_KEY);
        Log.i(LOG_TAG, mBuilder.toString());
    }

    public String getApiString() {
        return mBuilder.toString();
    }

    private void addQueryParam(String key, String value) {
        appendSeparator();
        mBuilder.append(key).append("=").append(value);
    }

    private void appendSeparator() {
        if (mBuilder.indexOf("?") == -1) {
            mBuilder.append("?");
        } else {
            mBuilder.append("&");
        }
    }

    public void filter(Filters filterType, String filter) {
        if (!isFiltered) {
            appendSeparator();
            mBuilder.append("/search/movie?");
            isFiltered = true;
        }
        mBuilder.append(filterType.name()).append("=").append(filter);
    }

    public void sort(SortType sortType) {
        if(!isSorted) {
            appendSeparator();
            mBuilder.append("sort_by=").append(sortType.getValue());
            isSorted = true;
        }
    }

    public static String getPopularUrl(){
        return POPULAR_URL_COMPLETE;
    }

    public static String getBackdropUrl(String extra){
        return BASE_IMG_URL+extra;
    }

}
