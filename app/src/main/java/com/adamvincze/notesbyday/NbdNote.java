package com.adamvincze.notesbyday;

//The data class for the note instance

import org.joda.time.LocalDate;

import java.io.Serializable;

import lombok.Data;

@Data class NbdNote implements Serializable {

    private LocalDate noteDate;
    private String noteText;

    NbdNote(LocalDate date, String text) {

        this.noteDate = date;
        this.noteText = text;

    }
}
