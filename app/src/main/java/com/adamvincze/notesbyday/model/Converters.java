package com.adamvincze.notesbyday.model;

import android.arch.persistence.room.TypeConverter;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * Type converters the Joda time date fields
 */
public class Converters {

    @TypeConverter
    public static LocalDate toDate(String value) {
        return value == null ? null : new LocalDate(value);
    }

    @TypeConverter
    public static String dateToString(LocalDate date) {
        return date == null ? null : date.toString();
    }

    @TypeConverter
    public static LocalDateTime toDateTime(String value) {
        return value == null ? null : new LocalDateTime(value);
    }

    @TypeConverter
    public static String datetimeToString(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.toString();
    }

}
