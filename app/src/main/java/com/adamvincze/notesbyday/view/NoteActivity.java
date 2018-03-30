package com.adamvincze.notesbyday.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.adamvincze.notesbyday.Helpers;
import com.adamvincze.notesbyday.NbdApplication;
import com.adamvincze.notesbyday.R;
import com.adamvincze.notesbyday.model.Note;
import com.adamvincze.notesbyday.viewmodel.NoteActivityViewModel;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The note editor activity - view
 */
//TODO create a viewmodel for this
public class NoteActivity extends AppCompatActivity {

    NoteActivityViewModel noteViewModel;

    @BindView(R.id.note_toolbar) Toolbar noteToolbar;
    ActionBar noteActionBar;
    @BindView(R.id.date_chip_view) TextView dateChip;
    @BindView(R.id.note_edit_text) TextInputEditText noteEditor;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);

        noteViewModel = ViewModelProviders.of(this).get(NoteActivityViewModel.class);

        //Getting the note object out of the intent bundle if exists, if not, creation of a new one
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            noteViewModel.setNote((Note) extras.getSerializable("note"));
            if (extras.getBoolean("isNewNote")) noteViewModel.setNew();
        } else {
            Note newNote = new Note();
            newNote.setDate(new LocalDate());
            newNote.setAdded(new LocalDateTime());
            noteViewModel.setNote(newNote);
            noteViewModel.setNew();
        }

        //The Toolbar
        setSupportActionBar(noteToolbar);
        noteActionBar = getSupportActionBar();
        assert noteActionBar != null;
        noteActionBar.setDisplayShowTitleEnabled(false);
        //TODO: create that fancy effect from Telegram for cancel if the EditText is empty
        noteActionBar.setDisplayHomeAsUpEnabled(true);

        //The Chip
        dateChip.setText(Helpers.formatDate(noteViewModel.getSelectedDate()));

        //The editor area
        noteEditor.setText(noteViewModel.getNote().getText());
        noteEditor.setSelection(noteEditor.getText().length());

    }

    /**
     * Listening to MenuItem clicks and back buttons
     * @param item: the MenuItem clicked
     * @return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String noteText = noteEditor.getText().toString();
        //TODO: clean up this mess
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button

            case android.R.id.home: {
                Intent backButton = NavUtils.getParentActivityIntent(this);
                assert backButton != null;
                //TODO: clear this branch when fancy Telegram effect is ready (?)

                //
                if (noteText.trim().equals("")) {
                    if (!noteViewModel.isNew()) {
                        backButton.putExtra("id", noteViewModel.getNote().getId());
                    }
                    setResult(NbdApplication.EMPTY_NOTE_RESULT, backButton);
                } else {
                    noteViewModel.getNote().setText(noteText);
                    //TODO only change setEdited if the text changed in the process
                    if (!noteViewModel.isNew()) {
                        noteViewModel.getNote().setEdited(new LocalDateTime());
                    }
                    backButton.putExtra("note", noteViewModel.getNote());
                    setResult(RESULT_OK, backButton);
                    //Toast.makeText(getApplicationContext(), R.string.note_saved, Toast.LENGTH_SHORT).show();
                }

                if (NavUtils.shouldUpRecreateTask(this, backButton)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    //TODO: prepare properly for notes from other apps, right now the note is not sent back, and the app is not prepared fro sharing to new note
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(backButton)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, backButton);
                }
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);

        }

    }

}
