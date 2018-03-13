package com.adamvincze.notesbyday.repository;

import android.arch.lifecycle.LiveData;

import com.adamvincze.notesbyday.model.Note;

import org.joda.time.LocalDate;

import java.util.List;

public interface NoteRepository {

    LiveData<Note> findById(int id);

    LiveData<List<Note>> findAll();

    LiveData<List<Note>> findByDay(LocalDate date);

    long insert(Note note);

    int delete(Note note);

}
