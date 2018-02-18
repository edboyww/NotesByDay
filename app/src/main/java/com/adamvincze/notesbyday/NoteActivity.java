package com.adamvincze.notesbyday;

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

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class NoteActivity extends AppCompatActivity {

    LocalDate selectedDate;
    NbdNote note;

    Toolbar noteToolbar;
    ActionBar noteActionBar;
    TextView dateChip;
    TextInputEditText noteEditor;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //Getting the note object out of the intent bundle if exists, if not, creation of a new one
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            note = (NbdNote) extras.getSerializable("note");
            assert note != null;
            selectedDate = note.getDate();
        } else {
            selectedDate = new LocalDate();
            note = new NbdNote();
            note.setDate(selectedDate);
            note.setAdded(new LocalDateTime());
        }

        //The Toolbar
        noteToolbar = findViewById(R.id.note_toolbar);
        setSupportActionBar(noteToolbar);
        noteActionBar = getSupportActionBar();
        assert noteActionBar != null;
        noteActionBar.setDisplayShowTitleEnabled(false);
        //TODO: create that fancy effect from Telegram for cancel if the EditText is empty
        noteActionBar.setDisplayHomeAsUpEnabled(true);

        //The Chip
        dateChip = findViewById(R.id.date_chip_view);
        dateChip.setText(NbdHelper.formatDate(selectedDate));

        //The editor area
        noteEditor = findViewById(R.id.note_edit_text);
        noteEditor.setText(note.getText());

    }

    //Listening to the toolbar actions
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
                //What if the user deletes the full note text and pushes the back button. Then it is not a cancel...
                if (noteText.equals("")) {
                    setResult(NbdApplication.EMPTY_NOTE, backButton);
                } else {
                    note.setText(noteText);
                    //TODO only change setEdited if the text changed in teh process
                    note.setEdited(new LocalDateTime());
                    backButton.putExtra("note", note);
                    setResult(RESULT_OK, backButton);
                    Toast.makeText(getApplicationContext(), R.string.note_saved, Toast.LENGTH_SHORT).show();
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
