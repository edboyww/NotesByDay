package com.adamvincze.notesbyday;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Random;

import static com.adamvincze.notesbyday.NbdHelper.formatDate;


public class MainActivity extends AppCompatActivity {

    Toolbar myToolbar;
    TextView dateText;
    ImageButton previousButton;
    ImageButton nextButton;
    FloatingActionButton newNoteFab;
    RecyclerView mainListView;

    LocalDate selectedDate = new LocalDate();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //putting the toolbar on top in the support library
        myToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        //initially show the current date at the open of the app
        dateText = findViewById(R.id.current_date_view);
        dateText.setText(formatDate(selectedDate));

        //the previous day button on the note card
        previousButton = findViewById(R.id.previous_day_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.minusDays(1);
                dateText.setText(formatDate(selectedDate));
            }
        });

        //the next day button on the note card
        nextButton = findViewById(R.id.next_day_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.plusDays(1);
                dateText.setText(formatDate(selectedDate));
            }
        });

        //the New note FAB, passing the current date
        newNoteFab = findViewById(R.id.new_note_fab);
        newNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newNoteIntent = new Intent(MainActivity.this, NoteActivity.class);
                NbdNote newNote = new NbdNote();
                newNote.setDate(selectedDate);
                newNote.setAdded(new LocalDateTime());
                newNoteIntent.putExtra("note", newNote);
                startActivityForResult(newNoteIntent, NbdApplication.NEW_NOTE);
            }
        });

        mainListView = findViewById(R.id.main_list_view);

        //for testing purposes
        ArrayList<NbdNote> testList = sampleNoteList(5, selectedDate);

        NbdNoteAdapter adapter = new NbdNoteAdapter(this, testList);
        mainListView.setAdapter(adapter);
        mainListView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent fromNewNote) {

        //TODO: handle the result properly, this is only for testing purposes
        switch (requestCode) {
            case NbdApplication.NEW_NOTE:
                switch (resultCode) {
                    case RESULT_OK:
                        NbdNote newNote = (NbdNote) fromNewNote.getSerializableExtra("note");
                        //TODO: test if this solution works
                        selectedDate = newNote.getDate();
                        dateText.setText(formatDate(selectedDate));
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
    static ArrayList<NbdNote> sampleViewList(int n, LocalDate date) {

        ArrayList<NbdNote> list = new ArrayList<>();
        Lorem lorem = LoremIpsum.getInstance();
        Random random = new Random();

        int j = random.nextInt(n);
        for (int k = 0; k < j; k++) {
            NbdNote note = new NbdNote();
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
