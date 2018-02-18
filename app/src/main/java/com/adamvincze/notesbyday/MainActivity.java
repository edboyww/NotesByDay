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

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Toolbar myToolbar;
    TextView dateText;
    ImageButton previousButton;
    ImageButton nextButton;
    FloatingActionButton newNoteFab;
    RecyclerView mainListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //putting the toolbar on top in the support library
        myToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        //initially show the current date at the open of the app
        dateText = findViewById(R.id.current_date_view);
        dateText.setText(NbdHelper.formatDate(NbdApplication.getNbdDate()));

        //the previous day button on the note card
        previousButton = findViewById(R.id.previous_day_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NbdApplication.setNbdDate(NbdApplication.getNbdDate().minusDays(1));
                dateText.setText(NbdHelper.formatDate(NbdApplication.getNbdDate()));
            }
        });

        //the next day button on the note card
        nextButton = findViewById(R.id.next_day_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NbdApplication.setNbdDate(NbdApplication.getNbdDate().plusDays(1));
                dateText.setText(NbdHelper.formatDate(NbdApplication.getNbdDate()));
            }
        });

        //the New note FAB, passing the current date
        newNoteFab = findViewById(R.id.new_note_fab);
        newNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newNoteIntent = new Intent(MainActivity.this, NoteActivity.class);
                //startActivityForResult(newNoteIntent, 0);
                startActivity(newNoteIntent);
            }
        });

        mainListView = findViewById(R.id.main_list_view);

        //for testing purposes
        ArrayList<NbdNote> testList = NbdHelper.sampleNoteList(5, NbdApplication.getNbdDate());
//        Log.v("NbdNoteListTest", testList.toString());

        NbdNoteAdapter adapter = new NbdNoteAdapter(this, testList);
        mainListView.setAdapter(adapter);
        mainListView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onNewIntent(Intent fromNewNote) {

        if (fromNewNote.hasExtra("note")) Log.v("Note from intent", fromNewNote.getSerializableExtra("note").toString());

        super.onNewIntent(fromNewNote);

    }



}
