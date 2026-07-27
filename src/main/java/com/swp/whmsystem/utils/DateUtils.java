package com.swp.whmsystem.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class DateUtils {

    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(dateStr.trim(), formatter);
        } catch (DateTimeParseException | IllegalArgumentException e) {
            return null;
        }
    }

    public static LocalDate parseStrictDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);
            return LocalDate.parse(dateStr.trim(), formatter);
        } catch (DateTimeParseException | IllegalArgumentException e) {
            return null;
        }
    }
}
