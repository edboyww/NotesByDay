package com.adamvincze.notesbyday.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * Room DB for the notes
 */
@Database(entities = {Note.class}, version = NoteDatabase.VERSION)
@TypeConverters({Converters.class})
public abstract class NoteDatabase extends RoomDatabase {

    static final int VERSION = 1;

    private static NoteDatabase INSTANCE;

    public static NoteDatabase getDatabase(Context context) {
        synchronized (NoteDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE =
                        Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_db")
                                .build();
            }
        }
        return INSTANCE;
    }

    public abstract NoteDao getNoteDao();

}
