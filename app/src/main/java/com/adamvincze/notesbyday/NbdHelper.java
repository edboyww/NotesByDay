package com.adamvincze.notesbyday;

import android.content.Context;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.Random;

//Collecting here all the helper methods in the app
final class NbdHelper {

    //Formatting a Joda LocalDate object as <long local date>, <day of the week> or a day string
    static String formatDate(LocalDate date) {
        Context context = NbdApplication.getNbdContext();
        LocalDate comp = LocalDate.now();
        if (date.equals(comp)) return context.getString(R.string.today);
        if (date.equals(comp.plusDays(1))) return context.getString(R.string.tomorrow);
        if (date.equals(comp.plusDays(2))) return context.getString(R.string.day_after_tomorrow);
        if (date.equals(comp.minusDays(1))) return context.getString(R.string.yesterday);
        if (date.equals(comp.minusDays(2))) return context.getString(R.string.day_before_yesterday);
        return DateTimeFormat.forPattern("yyyy MMM d, EEE").print(date);
    }

    //temporary implementation to make a list of 5-10 notes plus and minus n days from a given Localdate
    //TODO cleanup after finalizing note handling
    static ArrayList<NbdNote> sampleNoteList(int n, LocalDate date) {

        ArrayList<NbdNote> list = new ArrayList<>();
        Lorem lorem = LoremIpsum.getInstance();
        Random r = new Random();

        int j = r.nextInt(5);
        for (int k = 0; k < (5 + j); k++)
            list.add(new NbdNote(date, formatDate(date) + " - " + lorem.getWords(5,10)));

        for (int i = 0; i < n; i++) {

            j = r.nextInt(5);
            LocalDate plus = date.plusDays(i);
            for (int k = 0; k < (5 + j); k++)
                list.add(new NbdNote(plus, formatDate(plus) + " - " + lorem.getWords(5,10)));

            j = r.nextInt(5);
            LocalDate minus = date.minusDays(i);
            for (int k = 0; k < (5 + j); k++)
                list.add(new NbdNote(minus, formatDate(minus) + " - " + lorem.getWords(5,10)));
        }

        return list;

    }

}
