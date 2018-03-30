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
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home: {
                navigateUp();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Handling the Back button
     */
    @Override
    public void onBackPressed() {
        navigateUp();
        super.onBackPressed();
    }

    /**
     * Providing the up navigation and putting together the Intent with the result
     */
    private void navigateUp() {

        String noteText = noteEditor.getText().toString();
        Intent resultIntent = NavUtils.getParentActivityIntent(this);

        if (noteText.trim().equals("")) {
            if (!noteViewModel.isNew()) {
                resultIntent.putExtra("id", noteViewModel.getNote().getId());
            }
            setResult(NbdApplication.EMPTY_NOTE_RESULT, resultIntent);
        } else {
            noteViewModel.getNote().setText(noteText.trim());
            Log.d("Note isNew", Boolean.toString(noteViewModel.isNew()));
            Log.d("Note isEdited", Boolean.toString(noteViewModel.isEdited()));
            if (!noteViewModel.isNew() && noteViewModel.isEdited()) {
                noteViewModel.getNote().setEdited(new LocalDateTime());
            }
            resultIntent.putExtra("note", noteViewModel.getNote());
            setResult(RESULT_OK, resultIntent);
            //Toast.makeText(getApplicationContext(), R.string.note_saved, Toast.LENGTH_SHORT).show();
        }

        // If this activity is NOT part of this app's task, create a new task
        // when navigating up, with a synthesized back stack.
        if (NavUtils.shouldUpRecreateTask(this, resultIntent)) {
            //TODO: it is not working right now
            TaskStackBuilder.create(this)
                    // Add all of this activity's parents to the back stack
                    .addNextIntentWithParentStack(resultIntent)
                    // Navigate up to the closest parent
                    .startActivities();
        }
        // Else if this activity is part of this app's task, so simply
        // navigate up to the logical parent activity.
        else {
            NavUtils.navigateUpTo(this, resultIntent);
        }

    }


}
