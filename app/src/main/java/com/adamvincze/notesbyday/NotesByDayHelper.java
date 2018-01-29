package com.adamvincze.notesbyday;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by adamv on 2018. 01. 27..
 * Collecting here all the helper methods in the app
 */

final class NotesByDayHelper {

    static String nbdFormatDate(Date date) {

        return DateFormat.getDateInstance().format(date) + ", " + android.text.format.DateFormat.format("EEEE", date);
    }

    static String nbdFormatCalendar(Calendar cal) {
        Date date = cal.getTime();
        return DateFormat.getDateInstance().format(date) + ", " + android.text.format.DateFormat.format("EEEE", date);
    }

    static void nextDayButtonHelper(Calendar calendar) { calendar.add(Calendar.DATE, 1); }

    static void previousDayButtonHelper(Calendar calendar) { calendar.add(Calendar.DATE, -1); }


}
