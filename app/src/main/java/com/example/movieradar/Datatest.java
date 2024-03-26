package com.example.movieradar;

import android.os.Build;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Datatest {
    public static int getMinage() {
        return minage;
    }

    static int minage = 12;
    public static boolean checkVarChar(String str, int lgh) {
    if (str != null) {
        if (str.length() <= lgh) {
            // Check if the string contains only non-Unicode characters
            return !containsUnicodeCharacter(str);
        }
    }
    // If any of the conditions fail, return false
    return false;
}

    //Methode checkt voor NVarChar tegen bepaalde lengte
    public static boolean checkNVarChar(String str, Integer lgh) {
        if (!str.equals("")) {
            return str.length() < lgh + 1;
        }
        // If any of the conditions fail, return false
        return false;
    }

    //Methode checkt string tegen unicode characters
    public static boolean containsUnicodeCharacter(String str) {
        // Use a regular expression to check if the string contains any Unicode
        // character
        return Pattern
                .compile("\\p{InBasicLatin}\\p{InLatin-1Supplement}-\\p{InLatinExtended-C}&&[^\\p{InBasicLatin}]")
                .matcher(str).find();
    }

    //Methode check of een number onder een limiet blijft.
    public static boolean checkValidNumber(String str, int limit) {
        if (str != null && !str.isEmpty()) {
            try {

                return Integer.parseInt(str) > 0 && Integer.parseInt(str) <= limit;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    //Methode check of een email adress correct is geformateerd
    public static boolean checkEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    public static boolean checkBirthDate(LocalDate date) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (date.isBefore(LocalDate.now())) {
                return LocalDate.now().getYear() - date.getYear() >= minage;
            }
        }
        return false;
    }
    public static boolean checkCijfer(short cijfer) {
        return cijfer >= 1 && cijfer <= 10;
    }
}
