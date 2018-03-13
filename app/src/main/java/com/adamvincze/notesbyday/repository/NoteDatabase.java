package com.adamvincze.notesbyday.repository;

//Room DB for the notes

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.adamvincze.notesbyday.model.Note;
import com.adamvincze.notesbyday.model.NoteDao;

@Database(entities = {Note.class}, version = NoteDatabase.VERSION)
@TypeConverters({ConvertersForNoteDb.class})
public abstract class NoteDatabase extends RoomDatabase {

    static final int VERSION = 1;

    public abstract NoteDao getNoteDao();

}
