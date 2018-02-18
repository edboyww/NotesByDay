package com.adamvincze.notesbyday;

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

public class NoteActivity extends AppCompatActivity {

    Toolbar noteToolbar;
    TextView dateChip;
    TextInputEditText noteEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        noteToolbar = findViewById(R.id.note_toolbar);
        dateChip = findViewById(R.id.date_chip_view);
        noteEditor = findViewById(R.id.note_edit_text);

        setSupportActionBar(noteToolbar);
        ActionBar noteActionBar = getSupportActionBar();
        assert noteActionBar != null;
        noteActionBar.setDisplayShowTitleEnabled(false);
        noteActionBar.setDisplayHomeAsUpEnabled(true);

        //Setting the text of the date chip with the date or hide it if the date is nonexistent
        dateChip.setText(NbdHelper.formatDate(NbdApplication.getNbdDate()));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                assert upIntent != null;
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                        // Add all of this activity's parents to the back stack
                        .addNextIntentWithParentStack(upIntent)
                        // Navigate up to the closest parent
                        .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NbdNote note = new NbdNote(NbdApplication.getNbdDate(),noteEditor.getText().toString());
                    upIntent.putExtra("note", note);
                    //setResult(RESULT_OK, upIntent);
                    NavUtils.navigateUpTo(this, upIntent);
                }
                Toast.makeText(getApplicationContext(), R.string.note_saved, Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

}
