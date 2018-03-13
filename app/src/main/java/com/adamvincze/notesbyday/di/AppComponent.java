package com.adamvincze.notesbyday.di;

import android.app.Application;

import com.adamvincze.notesbyday.model.NoteDao;
import com.adamvincze.notesbyday.repository.NoteDatabase;
import com.adamvincze.notesbyday.repository.NoteRepository;
import com.adamvincze.notesbyday.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(dependencies = {}, modules = {AppModule.class, RoomModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    NoteDao noteDao();

    NoteDatabase noteDatabase();

    NoteRepository noteRepository();

    Application application();

}