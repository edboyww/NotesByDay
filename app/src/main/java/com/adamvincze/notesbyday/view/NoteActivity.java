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

    NoteActivityViewModel viewModel;

    @BindView(R.id.note_toolbar) Toolbar toolbar;
    ActionBar actionBar;
    @BindView(R.id.date_chip_view) TextView dateChip;
    @BindView(R.id.note_edit_text) TextInputEditText noteEditor;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(NoteActivityViewModel.class);

        //Getting the note object out of the intent bundle if exists, if not, creation of a new one
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            viewModel.setNote((Note) extras.getSerializable("note"));
            if (extras.getBoolean("isNewNote")) viewModel.setNew();
        } else {
            Note newNote = new Note();
            newNote.setDate(new LocalDate());
            newNote.setAdded(new LocalDateTime());
            viewModel.setNote(newNote);
            viewModel.setNew();
        }

        //The Toolbar
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        //TODO: create that fancy effect from Telegram for cancel if the EditText is empty
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (!viewModel.isNew()) {
            actionBar.setTitle(R.string.edit_note_title);
        }

        //The Chip
        dateChip.setText(Helpers.formatDate(viewModel.getSelectedDate()));

        //The editor area
        noteEditor.setText(viewModel.getNote().getText());
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
            case android.R.id.home: {
                // Respond to the action bar's Up/Home button
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

        String fromEditor = noteEditor.getText().toString();
        Intent resultIntent = NavUtils.getParentActivityIntent(this);

        if (fromEditor.trim().equals("")) {
            //If the trimmed contents of the text area is empty
            setResult(NbdApplication.EMPTY_NOTE_RESULT, resultIntent);
            if (!viewModel.isNew()) {
                resultIntent.putExtra("id", viewModel.getNote().getId());
                Toast.makeText(
                        getApplicationContext(),
                        R.string.note_deleted,
                        Toast.LENGTH_SHORT
                        ).show();
            }
        } else {
            //Pass back the note with the text
            viewModel.getNote().setText(fromEditor.trim());
            resultIntent.putExtra("note", viewModel.getNote());
            setResult(RESULT_OK, resultIntent);
            if (!viewModel.isNew() && viewModel.isEdited()) {
                viewModel.getNote().setEdited(new LocalDateTime());
            }
            Toast.makeText(
                    getApplicationContext(),
                    R.string.note_saved,
                    Toast.LENGTH_SHORT
            ).show();
        }

        if (NavUtils.shouldUpRecreateTask(this, resultIntent)) {
            // If this activity is NOT part of this app's task, create a new task
            // when navigating up, with a synthesized back stack.
            //TODO: it is not working right now
            TaskStackBuilder.create(this)
                    // Add all of this activity's parents to the back stack
                    .addNextIntentWithParentStack(resultIntent)
                    // Navigate up to the closest parent
                    .startActivities();
        }
        else {
            // Else if this activity is part of this app's task, so simply
            // navigate up to the logical parent activity.
            NavUtils.navigateUpTo(this, resultIntent);
        }

    }


}
