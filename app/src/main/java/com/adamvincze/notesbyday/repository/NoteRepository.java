package com.adamvincze.notesbyday.repository;

import android.arch.lifecycle.LiveData;

import com.adamvincze.notesbyday.data.Note;

import java.util.List;

public interface NoteRepository {

    LiveData<Note> findById(int id);

    LiveData<List<Note>> findAll();

    long insert(Note product);

    int delete(Note product);

}
