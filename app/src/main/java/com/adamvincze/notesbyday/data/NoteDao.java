package com.adamvincze.notesbyday.data;

//DAO for the Note class

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM Note WHERE id=:id")
    LiveData<Note> findById(int id);

    @Query("SELECT * FROM Note")
    LiveData<List<Note>> findAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Note product);

    @Delete
    int delete(Note product);

}

