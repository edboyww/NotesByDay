package com.adamvincze.notesbyday;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //putting the toolbar on top in the support library
        Toolbar myToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        //initially show the current date at the open of the app
        final TextView dateText = findViewById(R.id.current_date_view);
        dateText.setText(NbdHelper.formatDate(NbdApplication.getNbdDate()));

        //the previous day button on the note card
        ImageButton previousButton = findViewById(R.id.previous_day_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NbdApplication.setNbdDate(NbdApplication.getNbdDate().minusDays(1));
                dateText.setText(NbdHelper.formatDate(NbdApplication.getNbdDate()));
            }
        });

        //the next day button on the note card
        ImageButton nextButton = findViewById(R.id.next_day_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NbdApplication.setNbdDate(NbdApplication.getNbdDate().plusDays(1));
                dateText.setText(NbdHelper.formatDate(NbdApplication.getNbdDate()));
            }
        });

        //the New note FAB, passing the current date
        FloatingActionButton newNoteFab = findViewById(R.id.new_note_fab);
        newNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newNoteIntent = new Intent(MainActivity.this, NoteActivity.class);
                startActivity(newNoteIntent);
            }
        });

        RecyclerView mainListView = findViewById(R.id.main_list_view);

        //for testing purposes
        ArrayList<NbdNote> testList = NbdHelper.sampleNoteList(5, NbdApplication.getNbdDate());
//        Log.v("NbdNoteListTest", testList.toString());

        NbdNoteAdapter adapter = new NbdNoteAdapter(this, testList);
        mainListView.setAdapter(adapter);
        mainListView.setLayoutManager(new LinearLayoutManager(this));

        //mostly invisible button to reach the test layout
//        Button testButton = findViewById(R.id.test_button);
//        testButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent testIntent = new Intent(MainActivity.this, TestActivity.class);
//                startActivity(testIntent);
//            }
//        });

    }

}
