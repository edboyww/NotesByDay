package com.adamvincze.notesbyday;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

//Collecting here all the helper methods in the app
final class NotesByDayHelper {

    //Formatting a date object DEPRECATED
    static String nbdFormatDate(Date date) {

        return DateFormat.getDateInstance().format(date) + ", " + android.text.format.DateFormat.format("EEEE", date);
    }

    //Formatting a Calendar object as <long local date>, <day of the week>
    static String nbdFormatCalendar(Calendar cal) {
        Date date = cal.getTime();

        //TODO compare date to calendar and return day string accordingly (Today, Tomorrow, Yesterday, Day before yesterday)
        //Date today = Calendar.getInstance().getTime(); //is it necessary?

        return DateFormat.getDateInstance().format(date) + ", " + android.text.format.DateFormat.format("EEEE", date);
    }

    //adds a day to a calendar
    static void nextDayButtonHelper(Calendar calendar) { calendar.add(Calendar.DATE, 1); }

    //removes a day from calendar
    static void previousDayButtonHelper(Calendar calendar) { calendar.add(Calendar.DATE, -1); }


}
