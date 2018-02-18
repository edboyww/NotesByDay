package com.adamvincze.notesbyday;

//The data class for the note instance

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.io.Serializable;

import lombok.Data;

@Data class NbdNote implements Serializable {

    //private final int id;
    private LocalDate date;
    private String text;
    private LocalDateTime added;
    private LocalDateTime edited;

}
