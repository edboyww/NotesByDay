package com.adamvincze.notesbyday;

//The data class for the note instance

import org.joda.time.LocalDate;

import lombok.Data;

@Data class NbdNote {
    private LocalDate noteDate;
    private String noteText;

    NbdNote(LocalDate date, String text) {
        this.noteDate = date;
        this.noteText = text;
    }
}
