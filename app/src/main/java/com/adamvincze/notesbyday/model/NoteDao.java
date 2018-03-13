package com.adamvincze.notesbyday.model;

//DAO for the Note class

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.joda.time.LocalDate;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM Note WHERE id=:id")
    LiveData<Note> findById(int id);

    @Query("SELECT * FROM Note")
    LiveData<List<Note>> findAll();

    @Query("SELECT * FROM Note WHERE date=:date")
    LiveData<List<Note>> findByDay(LocalDate date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Note note);

    @Delete
    int delete(Note note);

}

