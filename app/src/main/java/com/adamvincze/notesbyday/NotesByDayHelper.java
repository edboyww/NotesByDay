package com.adamvincze.notesbyday;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

//Collecting here all the helper methods in the app
final class NotesByDayHelper {

    //Formatting a Joda LocalDate object as <long local date>, <day of the week>
    static String nbdFormatDate(LocalDate date) {

        //TODO compare date to calendar and return day string accordingly (Today, Tomorrow, Yesterday, Day before yesterday)

        return DateTimeFormat.forStyle("F").print(date);
    }

    //adds a day to a calendar
    static void nextDayButtonHelper(LocalDate date) { date.plusDays(1); }

    //removes a day from calendar
    static void previousDayButtonHelper(LocalDate date) { date.minusDays(1); }


}
