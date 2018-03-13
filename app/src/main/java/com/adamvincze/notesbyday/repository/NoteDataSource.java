package com.adamvincze.notesbyday.repository;


import android.arch.lifecycle.LiveData;

import com.adamvincze.notesbyday.model.Note;
import com.adamvincze.notesbyday.model.NoteDao;

import org.joda.time.LocalDate;

import java.util.List;

import javax.inject.Inject;

public class NoteDataSource implements NoteRepository {

    private NoteDao noteDao;

    @Inject
    public NoteDataSource(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    public LiveData<Note> findById(int id) {
        return noteDao.findById(id);
    }

    @Override
    public LiveData<List<Note>> findAll() {
        return noteDao.findAll();
    }

    @Override
    public LiveData<List<Note>> findByDay(LocalDate date) {
        return noteDao.findByDay(date);
    }

    @Override
    public long insert(Note note) {
        return noteDao.insert(note);
    }

    @Override
    public int delete(Note note) {
        return noteDao.delete(note);
    }

}
