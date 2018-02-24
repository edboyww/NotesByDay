package com.adamvincze.notesbyday;

import org.joda.time.LocalDate;

import java.util.ArrayList;

//List of the note objects for a selected day

public class NbdDayList extends ArrayList {
    public LocalDate date;

    public NbdDayList(LocalDate theDate) {
        this.date = theDate;
    }

    @Override
    public
}
