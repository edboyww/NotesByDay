package com.adamvincze.notesbyday.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.adamvincze.notesbyday.model.Note;
import com.adamvincze.notesbyday.model.NoteDatabase;
import com.adamvincze.notesbyday.model.NoteRepository;

import org.joda.time.LocalDate;

import java.util.List;

/**
 * ViewModel class for the main Activity
 */
public class MainListViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;

    public MainListViewModel(Application application) {
        super(application);
        noteRepository = NoteRepository.getInstance(application);
    }

    public LiveData<List<Note>> notesByDay(LocalDate date) {
        return noteRepository.getNotesByDay(date);
    }

    public void insert

}
