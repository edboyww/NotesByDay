package com.adamvincze.notesbyday.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import org.joda.time.LocalDate;

import java.util.List;

/**
 * Repository of the Main NoteList
 */

public class NoteRepository {

    private static NoteRepository INSTANCE = null;
    private NoteDao noteDao;
//    public Map<LocalDate, LiveData<List<Note>>> noteCache;
//    private LiveData<List<Note>> allNotes;
//    private LiveData<List<Note>> dailyNotes;

    private NoteRepository(Application application) {
        NoteDatabase db = NoteDatabase.getDatabase(application);
        noteDao = db.getNoteDao();
//        allNotes = noteDao.findAll();/
    }

    /**
     * Getting a single instance of the class, creating if necessary
     */
    public static NoteRepository getInstance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new NoteRepository(application);
        }
        return INSTANCE;
    }

    /**
     * The insert Note async task
     */
    public void insertNote(Note note) { new InsertAsyncTask(noteDao).execute(note); }
    private static class InsertAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao asyncTaskDao;

        InsertAsyncTask(NoteDao dao) { this.asyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Note... notes) {
            asyncTaskDao.insert(notes[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }


    /**
     * The delete Note async task
     */
    public void deleteNote(Note note) { new DeleteAsyncTask(noteDao).execute(note); }
    private static class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao asyncTaskDao;

        DeleteAsyncTask(NoteDao dao) { this.asyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Note... notes) {
            asyncTaskDao.delete(notes[0]);
            return null;
        }
    }

    /**
     * Get daily notes. Not async.
     */
    public LiveData<List<Note>> getNotesByDay(LocalDate date) {
        return noteDao.findByDay(date);
    }

    /**
     * Gat a single note from the DB. Not sync.
     */
    public LiveData<Note> getNote(int id) {
        return noteDao.findById(id);
    }

//    /**
//     * Task for getting a day's note list from the DB
//     */
//    public void fillDaysCache(LocalDate date) { new DailyNoteAsyncTask(noteDao).execute(date); }
//    private static class DailyNoteAsyncTask extends AsyncTask<LocalDate, Void, LiveData<List<Note>>> {
//
//        private NoteDao asyncTaskDao;
//
//        DailyNoteAsyncTask(NoteDao dao) { this.asyncTaskDao = dao; }
//
//        @Override
//        protected LiveData<List<Note>> doInBackground(final LocalDate... localDates) {
//            return asyncTaskDao.findByDay(localDates[0]);
//        }
//    }


}
