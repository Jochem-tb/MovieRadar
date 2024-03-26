package com.example.movieradar.API;

public enum SortType {
    POPULARITY_DESC("popularity.desc"),
    POPULARITY_ASC("popularity.asc"),
    REVENUE_ASC("revenue.asc"),
    REVENUE_DESC("revenue.desc"),
    TITLE_ASC("title.asc"),
    TITLE_DESC("title.desc"),
    VOTE_AVERAGE_ASC("vote_average.asc"),
    VOTE_AVERAGE_DESC("vote_average.desc");
    ;

    private final String value;
    SortType(String s) {
        this.value = s;
    }

    public String getValue() {
        return this.value;
    }
}
