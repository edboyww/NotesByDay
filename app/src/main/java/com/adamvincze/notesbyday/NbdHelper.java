package com.adamvincze.notesbyday;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

//Collecting here all the helper methods in the app
final class NbdHelper {

    //Formatting a Joda LocalDate object as <long local date>, <day of the week> or a day string
    static String nbdFormatDate(LocalDate date) {
        //TODO compare date to calendar and return day string accordingly (Today, Tomorrow, Yesterday, Day before yesterday)
//        LocalDate comp = LocalDate.now();
//        if (date.equals(comp)) return context.getString(R.string.today); else
//        if (date.equals(comp.plusDays(1))) return context.getString(R.string.tomorrow);
//        if (date.equals(comp.plusDays(2))) return context.getString(R.string.day_after_tomorrow);
//        if (date.equals(comp.minusDays(1))) return context.getString(R.string.yesterday);
//        if (date.equals(comp.minusDays(2))) return context.getString(R.string.day_before_yesterday);
        return DateTimeFormat.forPattern("yyyy MMM d, EEE").print(date);
    }

}
