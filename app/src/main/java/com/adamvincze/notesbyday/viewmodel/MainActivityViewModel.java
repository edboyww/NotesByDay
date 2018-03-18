package com.adamvincze.notesbyday.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.adamvincze.notesbyday.model.Note;
import com.adamvincze.notesbyday.model.NoteRepository;

import org.joda.time.LocalDate;

import java.util.List;

/**
 * ViewModel class for the main Activity
 */
public class MainActivityViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LocalDate selectedDate;
    private MutableLiveData<List<Note>> notesDataByDate;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        noteRepository = NoteRepository.getInstance(application);
    }

    public void setDate(LocalDate toDate) {
        this.selectedDate = toDate;
        //notesDataByDate = new MutableLiveData<>();
        notesDataByDate = noteRepository.getNotesByDate(toDate);
        //debugging
            try {
                Log.d("Note list:", notesDataByDate.getValue().toString());
            }
            catch (NullPointerException npe) {
                Log.d("Note list", "NULL");
            }
    }

    public LocalDate getSelectedDate() { return selectedDate; }

    public MutableLiveData<List<Note>> refreshNotesDataByDate() {
        return notesDataByDate;
    }

    public void putNote(Note note) {
        noteRepository.insertNote(note);
    }

    public void deleteNote(Note note) {
        noteRepository.deleteNote(note);
    }

}
