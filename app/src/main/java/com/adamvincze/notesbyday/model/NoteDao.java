package com.adamvincze.notesbyday.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.joda.time.LocalDate;

import java.util.List;

/**
 * DAO for the Note class
 */
@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes WHERE id=:id")
    Note findById(int id);

    @Query("SELECT * FROM notes WHERE date=:date")
    List<Note> findByDay(LocalDate date);

    @Query("SELECT * FROM notes")
    List<Note> findAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Note note);

//    @Update
//    int update(Note note);

    @Delete
    int delete(Note note);

}

