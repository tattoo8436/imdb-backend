package com.example.demoimdb.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {
    private static final String DATE_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";
    public static boolean isValidDate(String dateStr) {
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(dateStr);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
