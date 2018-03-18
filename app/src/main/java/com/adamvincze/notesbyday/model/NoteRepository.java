package com.adamvincze.notesbyday.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import org.joda.time.LocalDate;

import java.util.List;

/**
 * Repository of the Main NoteList
 */

public class NoteRepository {

    private static NoteRepository INSTANCE = null;
    private NoteDao noteDao;
    MutableLiveData<List<Note>> dailyNotes = null;

    private NoteRepository(Application application) {
        NoteDatabase db = NoteDatabase.getDatabase(application);
        noteDao = db.getNoteDao();
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
     * Get daily notes async task. What TODO with the result?...
     */
    public MutableLiveData<List<Note>> getNotesByDate(LocalDate date) {
        new GetByDateAsyncTask(noteDao).execute(date);
        return dailyNotes;
    }
    private static class GetByDateAsyncTask extends AsyncTask<LocalDate, Void, List<Note>> {

        private NoteDao asyncTaskDao;

        GetByDateAsyncTask(NoteDao dao) { this.asyncTaskDao = dao; }

        @Override
        protected List<Note> doInBackground(final LocalDate... dates) {
            return asyncTaskDao.findByDay(dates[0]);
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            super.onPostExecute(notes);
            INSTANCE.dailyNotes.postValue(notes);
        }
    }

//    /**
//     * Get a single note from the DB. Not sync.
//     */
//    public MutableLiveData<Note> getNote(int id) {
//        MutableLiveData<Note> data = new MutableLiveData<>();
//        data.setValue(noteDao.findById(id));
//        return data;
//    }



}
