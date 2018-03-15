package com.adamvincze.notesbyday.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.adamvincze.notesbyday.NbdApplication;
import com.adamvincze.notesbyday.R;
import com.adamvincze.notesbyday.model.Note;
import com.adamvincze.notesbyday.viewmodel.MainListViewModel;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.adamvincze.notesbyday.Helpers.formatDate;

/**
 * The main activity - view
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_toolbar) Toolbar mainToolbar;
    @BindView(R.id.main_date_textview) TextView currentDateView;
    @BindView(R.id.previous_day_button) ImageButton previousButton;
    @BindView(R.id.next_day_button) ImageButton nextButton;
    @BindView(R.id.new_note_fab) FloatingActionButton newNoteFab;
    @BindView(R.id.main_list_view) RecyclerView mainListView;

    private MainListViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //putting the toolbar on top in the support library
        setSupportActionBar(mainToolbar);

        viewModel = ViewModelProviders.of(this).get(MainListViewModel.class);

        //initially show the current date at the open of the app
        currentDateView.setText(formatDate(viewModel.getSelectedDate()));

        final NoteListAdapter adapter = new NoteListAdapter(viewModel.getNotesByDate().getValue());
        mainListView.setAdapter(adapter);
        mainListView.setLayoutManager(new LinearLayoutManager(this));
        viewModel.getNotesByDate().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable final List<Note> notes) {
                // Update the cached copy of the words in the adapter.
                adapter.setNoteList(notes);
            }
        });

    }

    //listener of the previous day button on the note card
    @OnClick(R.id.previous_day_button)
    public void previousDay() {
        viewModel.setDate(viewModel.getSelectedDate().minusDays(1));
        currentDateView.setText(formatDate(viewModel.getSelectedDate()));
    }

    //listener of the next day button on the note card
    @OnClick(R.id.next_day_button)
    public void nextDay() {
        viewModel.setDate(viewModel.getSelectedDate().plusDays(1));
        currentDateView.setText(formatDate(viewModel.getSelectedDate()));
    }

    //listener of the New note FAB, passing the current date
    @OnClick(R.id.new_note_fab)
    public void newNote() {
        Intent newNoteIntent = new Intent(MainActivity.this, NoteActivity.class);
        Note newNote = new Note();
        newNote.setDate(viewModel.getSelectedDate());
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
                        viewModel.setDate(newNote.getDate());
                        currentDateView.setText(formatDate(viewModel.getSelectedDate()));
                        viewModel.putNote(newNote);
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

//    //temporary implementation to make a list of max n notes for a given Localdate
//    //TODO cleanup after finalizing note handling
//    static ArrayList<Note> sampleNoteList(int n, LocalDate date) {
//
//        ArrayList<Note> list = new ArrayList<>();
//        Lorem lorem = LoremIpsum.getInstance();
//        Random random = new Random();
//
//        int j = random.nextInt(n);
//        for (int k = 0; k < j; k++) {
//            Note note = new Note();
//            note.setDate(date);
//            note.setText(formatDate(date) + " - " + lorem.getWords(5, 10));
//            if (random.nextBoolean()) {
//                note.setAdded(new LocalDateTime()); //TODO: notgood!
//            } else note.setAdded(new LocalDateTime());
//            list.add(note);
//        }
//
//        return list;
//
//    }


}
