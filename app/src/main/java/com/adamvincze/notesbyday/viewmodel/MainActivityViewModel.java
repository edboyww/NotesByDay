package com.adamvincze.notesbyday.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.adamvincze.notesbyday.model.Note;
import com.adamvincze.notesbyday.model.NoteDao;
import com.adamvincze.notesbyday.model.NoteDatabase;

import org.joda.time.LocalDate;

import java.util.List;

/**
 * ViewModel class for the main Activity
 */
public class MainActivityViewModel extends AndroidViewModel {

    private NoteDatabase noteDatabase;
    private NoteDao noteDao;
    private MutableLiveData<LocalDate> selectedDate = new MutableLiveData<>();
    public final LiveData<List<Note>> notesData =
            Transformations.switchMap(
                    selectedDate,
                    date -> noteDao.selectByDay(date)
            );

        public MainActivityViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getDatabase(this.getApplication());
        noteDao = noteDatabase.getNoteDao();
    }

    /**
     * Changes the date on the UI
     * @param toDate: to set the LocalDate the notes of which we want to list
     */
    public void setDate(LocalDate toDate) {
        selectedDate.setValue(toDate);
    }

    /**
     * To get the selected date on the UI thread
     * @return the currently stored selected LocalDate
     */
    public LocalDate getSelectedDate() { return selectedDate.getValue(); }

    /**
     * Insert note async task
     * @param note: the Note object which is inserted
     */
    public void insertNote(Note note) {
        new InsertAsyncTask(noteDatabase).execute(note);
    }
    private static class InsertAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDatabase db;
        InsertAsyncTask(NoteDatabase ndb) {
            db = ndb;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            db.getNoteDao().insert(notes[0]);
            return null;
        }
    }

    /**
     * Delete note async task
     * @param id: the id of the Note object which is deleted
     */
    public void deleteNoteById(int id) {
        new DeleteAsyncTask(noteDatabase).execute(id);
    }
    private static class DeleteAsyncTask extends AsyncTask<Integer, Void, Void> {

        private NoteDatabase db;
        DeleteAsyncTask(NoteDatabase ndb) {
            db = ndb;
        }

        @Override
        protected Void doInBackground(Integer... ids) {
            db.getNoteDao().deleteByID(ids[0]);
            return null;
        }
    }

}
