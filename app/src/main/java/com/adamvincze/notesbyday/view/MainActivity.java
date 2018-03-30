package com.adamvincze.notesbyday.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.adamvincze.notesbyday.R;
import com.adamvincze.notesbyday.model.Note;
import com.adamvincze.notesbyday.viewmodel.MainActivityViewModel;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.adamvincze.notesbyday.Helpers.formatDate;
import static com.adamvincze.notesbyday.NbdApplication.*;

/**
 * The Main Activity - Note list
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_toolbar) Toolbar mainToolbar;
    @BindView(R.id.main_date_textview) TextView currentDateView;
    @BindView(R.id.main_previous_day_button) ImageButton previousButton;
    @BindView(R.id.main_next_day_button) ImageButton nextButton;
    @BindView(R.id.main_new_note_fab) FloatingActionButton newNoteFab;
    @BindView(R.id.main_list_view) RecyclerView mainListView;

    private MainActivityViewModel viewModel;
    private NoteListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //putting the toolbar on top in the support library
        setSupportActionBar(mainToolbar);

        //getting the VM and populating it with the initial date
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.setDate(new LocalDate());

        //initially show the current date at the open of the app
        currentDateView.setText(formatDate(viewModel.getSelectedDate()));

        //setting the adapter and layout for the main note list
        adapter = new NoteListAdapter(viewModel.notesData.getValue());
        mainListView.setAdapter(adapter);
        mainListView.setLayoutManager(new LinearLayoutManager(this));

        //setting the observer for the LiveData containing the list
        viewModel.notesData.observe(
            this,
            // Update the cached copy of the words in the adapter.
            notes -> adapter.updateList(notes)
        );

        //setting the click listeners for the note items
        mainListView.addOnItemTouchListener(new NoteTouchListener(
                this,
                mainListView,
                new NoteClickListener() {

                    @Override
                    public void onClick(View view, int position) {
                        Intent editNoteIntent = new Intent(MainActivity.this, NoteActivity.class);
                        editNoteIntent.putExtra("note", adapter.getItem(position));
                        editNoteIntent.putExtra("isNewNote", false);
                        startActivityForResult(editNoteIntent, EDIT_NOTE_INTENT);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        AlertDialog.Builder alert =
                                new AlertDialog.Builder(MainActivity.this).
                                    setMessage(R.string.note_alert).
                                    setPositiveButton(
                                            R.string.note_alert_yes,
                                            (dialog, which) -> {
                                                viewModel.deleteNoteById(adapter.getItem(position).getId());
                                                dialog.dismiss();
                                                //TODO: Implement the undo snackbar
                                            }
                                    ).
                                    setNegativeButton(
                                            R.string.note_alert_no,
                                            (dialog, which) -> dialog.dismiss()
                                    );
                        alert.create().show();
                    }
                }

        ));
    }

    /**
     * listener of the previous day button on the note card
     */
    @OnClick(R.id.main_previous_day_button)
    public void previousDay() {
        viewModel.setDate(viewModel.getSelectedDate().minusDays(1));
        currentDateView.setText(formatDate(viewModel.getSelectedDate()));
    }

    /**
     * listener of the next day button on the note card
     */
    @OnClick(R.id.main_next_day_button)
    public void nextDay() {
        viewModel.setDate(viewModel.getSelectedDate().plusDays(1));
        currentDateView.setText(formatDate(viewModel.getSelectedDate()));
    }

    /**
     * listener of the New note FAB, passing the current date
     */
    @OnClick(R.id.main_new_note_fab)
    public void newNote() {
        Intent newNoteIntent = new Intent(MainActivity.this, NoteActivity.class);
        Note newNote = new Note();
        newNote.setDate(viewModel.getSelectedDate());
        newNote.setAdded(new LocalDateTime());
        newNoteIntent.putExtra("note", newNote);
        newNoteIntent.putExtra("isNewNote", true);
        startActivityForResult(newNoteIntent, NEW_NOTE_INTENT);
    }

    /**
     * Getting back the Note object from NoteActivity
     * @param requestCode: the request code with which the Note intent is sent
     * @param resultCode: the result code
     * @param fromNoteActivity: the Intent from NoteActivity with the added or edited Note as an extra
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent fromNoteActivity) {

        switch (requestCode) {

            case NEW_NOTE_INTENT:
                switch (resultCode) {
                    case RESULT_OK:
                        Note newNote = (Note) fromNoteActivity.getSerializableExtra("note");
                        viewModel.setDate(newNote.getDate());
                        currentDateView.setText(formatDate(viewModel.getSelectedDate()));
                        viewModel.insertNote(newNote);
                        Log.v("New note from intent", newNote.toString());
                        break;
                    case EMPTY_NOTE_RESULT:
                        Log.v("New note from intent", "Doesn't exist");
                        break;
                    case RESULT_CANCELED:
                        Log.v("New note from intent", "RESULT_CANCELED");
                        break;
                    default:
                        Log.v("New note from intent", "Unexpected result");
                        break;
                }
                break;

            case EDIT_NOTE_INTENT:
                switch (resultCode) {
                    case RESULT_OK:
                        Note editedNote = (Note) fromNoteActivity.getSerializableExtra("note");
                        viewModel.setDate(editedNote.getDate());
                        currentDateView.setText(formatDate(viewModel.getSelectedDate()));
                        viewModel.insertNote(editedNote);
                        Log.v("Note edit from intent", editedNote.toString());
                        break;
                    case EMPTY_NOTE_RESULT:
                        int idToDelete = fromNoteActivity.getIntExtra("id", 0);
                        viewModel.deleteNoteById(idToDelete);
                        Log.v("Note edit from intent", (idToDelete + " deleted"));
                        break;
                    case RESULT_CANCELED:
                        Log.v("Note edit from intent", "RESULT_CANCELED");
                        break;
                    default:
                        Log.v("Note edit from intent", "Unexpected result");
                        break;
                }

            default:
                super.onActivityResult(requestCode, resultCode, fromNoteActivity);
                break;
        }
    }

}
