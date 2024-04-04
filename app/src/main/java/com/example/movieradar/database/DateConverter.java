package com.example.movieradar.database;

import androidx.room.TypeConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @TypeConverter
    public static Date toDate(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    @TypeConverter
    public static String fromDate(Date date) {
        if (date != null) {
            return dateFormat.format(date);
        }
        return null;
    }
}