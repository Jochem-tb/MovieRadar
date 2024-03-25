package com.example.movieradar;

import java.util.logging.Filter;

public class APIString {
    private String mReturn = "";

    private String LOG_TAG = "APIString";
    private String URL = "https://api.themoviedb.org/3";
    private String DISCOVER = "/discover/movie";
    private String DISCOVER_FILTER = "/discover/movie?";
    private String FINISH_KEY = "?api_key=731b0900535ff5476ae98c326ef7413c";
    private String FINISH_KEY_WITH_FILTER = "api_key=731b0900535ff5476ae98c326ef7413c";
    private Boolean isFiltered = false;
    private Boolean isSorted = false;

    public APIString(){
        this.mReturn = this.mReturn+URL;
    }

    public String finish(){
        if(isFiltered==false){
            return mReturn+FINISH_KEY;
        }
        else{
            return mReturn+FINISH_KEY_WITH_FILTER;
        }

    }

    public void isAdult(Boolean bool){
        if(isSorted == false) {
            if(isFiltered == false){
            mReturn = mReturn+DISCOVER_FILTER;
            isFiltered = true;
            }
            mReturn=mReturn+Filters.include_adult+"+"+bool+"&";
    }}

    public void filter(Filters filterType, String filter){
        if(isSorted == false) {
            if (!isFiltered) {
                mReturn += DISCOVER_FILTER;
                isFiltered = true;
            }
            String temp = null;
            switch (filterType) {
                case include_adult:
                    temp = Filters.include_adult.toString();
                    break;
                case with_genres:
                    temp = Filters.with_genres.toString();
                    break;
                case with_keywords:
                    temp = Filters.with_keywords.toString();
                    break;
                case with_release_type:
                    temp = Filters.with_release_type.toString();
                    break;
                case year:
                    temp = Filters.year.toString();
                    break;
                default:
                    break;
            }
            if (temp != null) {
                mReturn = mReturn + temp + "+" + filter + "&";
            }
        }
    }

    public void sort(SortType sortType) {
        if (isSorted == false){
            mReturn = mReturn + "sort_by=" + sortType.getValue() + "&";
        isSorted = true;
    }
    }
}