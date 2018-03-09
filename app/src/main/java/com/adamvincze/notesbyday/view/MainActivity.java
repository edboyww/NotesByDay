package com.adamvincze.notesbyday.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.adamvincze.notesbyday.NbdApplication;
import com.adamvincze.notesbyday.NoteListAdapter;
import com.adamvincze.notesbyday.R;
import com.adamvincze.notesbyday.data.Note;
import com.adamvincze.notesbyday.di.AppModule;
import com.adamvincze.notesbyday.di.RoomModule;
import com.adamvincze.notesbyday.repository.NoteRepository;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.adamvincze.notesbyday.NbdHelper.formatDate;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_toolbar) Toolbar mainToolbar;
    @BindView(R.id.date_bar) TextView currentDateView;
    @BindView(R.id.previous_day_button) ImageButton previousButton;
    @BindView(R.id.next_day_button) ImageButton nextButton;
    @BindView(R.id.new_note_fab) FloatingActionButton newNoteFab;
    @BindView(R.id.main_list_view) RecyclerView mainListView;

    @Inject public NoteRepository noteRepository;

    LocalDate selectedDate = new LocalDate();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaggerAppComponent.builder()
                .appModule(new AppModule(getApplication()))
                .roomModule(new RoomModule(getApplication()))
                .build()
                .inject(this);

        //putting the toolbar on top in the support library
        setSupportActionBar(mainToolbar);

        //initially show the current date at the open of the app
        currentDateView.setText(formatDate(selectedDate));

        //for testing purposes
        ArrayList<Note> testList = sampleNoteList(5, selectedDate);

        NoteListAdapter adapter = new NoteListAdapter(this, testList);
        mainListView.setAdapter(adapter);
        mainListView.setLayoutManager(new LinearLayoutManager(this));

    }

    //listener of the previous day button on the note card
    @OnClick(R.id.previous_day_button)
    public void previousDay() {
        selectedDate = selectedDate.minusDays(1);
        currentDateView.setText(formatDate(selectedDate));
    }

    //listener of the next day button on the note card
    @OnClick(R.id.next_day_button)
    public void nextDay() {
        selectedDate = selectedDate.plusDays(1);
        currentDateView.setText(formatDate(selectedDate));
    }

    //listener of the New note FAB, passing the current date
    @OnClick(R.id.new_note_fab)
    public void newNote() {
        Intent newNoteIntent = new Intent(MainActivity.this, NoteActivity.class);
        Note newNote = new Note();
        newNote.setDate(selectedDate);
        newNote.setAdded(new LocalDateTime());
        newNoteIntent.putExtra("note", newNote);
        startActivityForResult(newNoteIntent, NbdApplication.NEW_NOTE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent fromNewNote) {

        //TODO: handle the result properly, this is only for testing purposes
        switch (requestCode) {
            case NbdApplication.NEW_NOTE:
                switch (resultCode) {
                    case RESULT_OK:
                        Note newNote = (Note) fromNewNote.getSerializableExtra("note");
                        //TODO: test if this solution works
                        selectedDate = newNote.getDate();
                        currentDateView.setText(formatDate(selectedDate));
                        Log.v("Note from intent", newNote.toString());
                        break;
                    case NbdApplication.EMPTY_NOTE:
                        Log.v("Note from intent", "Doesn't exist");
                        break;
                    case RESULT_CANCELED:
                        Log.v("Note from intent", "RESULT_CANCELED");
                        break;
                    default:
                        Log.v("Note from intent", "Unexpected result");
                        break;
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, fromNewNote);
                break;
        }

    }

    //temporary implementation to make a list of max n notes for a given Localdate
    //TODO cleanup after finalizing note handling
    static ArrayList<Note> sampleNoteList(int n, LocalDate date) {

        ArrayList<Note> list = new ArrayList<>();
        Lorem lorem = LoremIpsum.getInstance();
        Random random = new Random();

        int j = random.nextInt(n);
        for (int k = 0; k < j; k++) {
            Note note = new Note();
            note.setDate(date);
            note.setText(formatDate(date) + " - " + lorem.getWords(5, 10));
            if (random.nextBoolean()) {

            } else note.setAdded();
            list.add(note);
        }

        return list;

    }

//    @Override
//    public void onNewIntent(Intent intent) {
//
//        if (intent.hasExtra("note")) Log.v("Note from intent", fromNewNote.getSerializableExtra("note").toString());
//
//        super.onNewIntent(intent);
//
//     }

}
