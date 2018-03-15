package com.adamvincze.notesbyday.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.adamvincze.notesbyday.model.Note;
import com.adamvincze.notesbyday.model.NoteRepository;

import org.joda.time.LocalDate;

import java.util.List;

/**
 * ViewModel class for the main Activity
 */
public class MainListViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LocalDate selectedDate;
    private LiveData<List<Note>> notesByDate;


    public MainListViewModel(@NonNull Application application) {
        super(application);
        noteRepository = NoteRepository.getInstance(application);
        this.setDate(new LocalDate());
    }

    public void setDate(LocalDate toDate) {
        this.selectedDate = toDate;
        notesByDate = noteRepository.getNotesByDate(toDate);
    }

    public LocalDate getSelectedDate() { return selectedDate; }

    public LiveData<List<Note>> getNotesByDate() { return notesByDate; }

    public void putNote(Note note) {
        noteRepository.insertNote(note);
    }

    public void deleteNote(Note note) {
        noteRepository.deleteNote(note);
    }

}
