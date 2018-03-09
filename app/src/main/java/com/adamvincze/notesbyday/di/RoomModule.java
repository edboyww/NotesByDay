package com.adamvincze.notesbyday.di;


import android.app.Application;
import android.arch.persistence.room.Room;

import com.adamvincze.notesbyday.data.NoteDao;
import com.adamvincze.notesbyday.repository.NoteDataSource;
import com.adamvincze.notesbyday.repository.NoteDatabase;
import com.adamvincze.notesbyday.repository.NoteRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private NoteDatabase noteDatabase;

    public RoomModule(Application mApplication) {
        noteDatabase = Room.databaseBuilder(mApplication, NoteDatabase.class, "note-db").build();
    }

    @Singleton
    @Provides
    NoteDatabase providesRoomDatabase() {
        return noteDatabase;
    }

    @Singleton
    @Provides
    NoteDao providesProductDao(NoteDatabase noteDatabase) {
        return noteDatabase.getNoteDao();
    }

    @Singleton
    @Provides
    NoteRepository productRepository(NoteDao noteDao) {
        return new NoteDataSource(noteDao);
    }


}
