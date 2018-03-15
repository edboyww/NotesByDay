package com.adamvincze.notesbyday.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.joda.time.LocalDate;

import java.util.List;

/**
 * DAO for the Note class
 */
@Dao
public interface NoteDao {

    @Query("SELECT * FROM Note WHERE id=:id")
    LiveData<Note> findById(int id);

    @Query("SELECT * FROM Note WHERE date=:date")
    LiveData<List<Note>> findByDay(LocalDate date);

    @Query("SELECT * FROM Note")
    LiveData<List<Note>> findAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Note note);

//    @Update
//    int update(Note note);

    @Delete
    int delete(Note note);

}

