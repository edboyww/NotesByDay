package com.adamvincze.notesbyday.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.adamvincze.notesbyday.model.Note;

import org.joda.time.LocalDate;

/**
 * ViewModel for the Note editor activity
 */
public class NoteActivityViewModel extends AndroidViewModel {

    private LocalDate selectedDate;
    private Note note;
    private boolean isNew = false;
    private String baseText;
    private LocalDate baseDate;

    public NoteActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void setSelectedDate(LocalDate date) {
        selectedDate = date;
        note.setDate(date);
    }

    public LocalDate getSelectedDate() { return selectedDate; }

    public void setNote(Note note) {
        this.note = note;
        selectedDate = note.getDate();
        baseText = note.getText();
        baseDate = note.getDate();
    }

    public Note getNote() { return note; }

    public void setNew() { isNew = true; }

    public boolean isNew() { return isNew; }

    public boolean isEdited() {
        return (!(note.getText().equals(baseText) && note.getDate().equals(baseDate)));
    }

}
