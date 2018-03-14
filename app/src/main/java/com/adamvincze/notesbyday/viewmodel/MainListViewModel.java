package com.adamvincze.notesbyday.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.adamvincze.notesbyday.model.Note;
import com.adamvincze.notesbyday.model.NoteDatabase;

import org.joda.time.LocalDate;

import java.util.List;

/**
 * ViewModel class for the main Activity
 */
public class MainListViewModel extends AndroidViewModel {

    private LiveData<List<Note>> noteList;

    private NoteDatabase noteDatabase;

    public MainListViewModel(Application application, LocalDate date) {
        super(application);
        noteDatabase = NoteDatabase.getDatabase(this.getApplication());
        noteList = noteDatabase.getNoteDao().findByDay(date);
    }

    public LiveData<List<Note>> getMainNoteList() {
        return noteList;
    }



}
