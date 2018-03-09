package com.adamvincze.notesbyday.repository;


import android.arch.lifecycle.LiveData;

import com.adamvincze.notesbyday.data.Note;
import com.adamvincze.notesbyday.data.NoteDao;

import java.util.List;

import javax.inject.Inject;

public class NoteDataSource implements NoteRepository {

    private NoteDao productDao;

    @Inject
    public NoteDataSource(NoteDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public LiveData<Note> findById(int id) {
        return productDao.findById(id);
    }

    @Override
    public LiveData<List<Note>> findAll() {
        return productDao.findAll();
    }

    @Override
    public long insert(Note product) {
        return productDao.insert(product);
    }

    @Override
    public int delete(Note product) {
        return productDao.delete(product);
    }

}
