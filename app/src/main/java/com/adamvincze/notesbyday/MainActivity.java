package com.adamvincze.notesbyday;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    protected Calendar dates = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        //Date chosenDate =  dates.getTime();

        //initially show the current date at the open of the app
        final TextView dateText = findViewById(R.id.current_date_view);
        dateText.setText(NotesByDayHelper.nbdFormatCalendar(dates));

        ImageButton previousButton = findViewById(R.id.previous_day_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
            NotesByDayHelper.previousDayButtonHelper(dates);
            dateText.setText(NotesByDayHelper.nbdFormatCalendar(dates));
            }
        });

        ImageButton nextButton = findViewById(R.id.next_day_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                NotesByDayHelper.nextDayButtonHelper(dates);
                dateText.setText(NotesByDayHelper.nbdFormatCalendar(dates));
            }
        });

        FloatingActionButton newNoteFab = findViewById(R.id.new_note_fab);
        newNoteFab.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the family category is clicked on.
            @Override
            public void onClick(View view) {
                Intent newNoteIntent = new Intent(MainActivity.this, NoteActivity.class);
                startActivity(newNoteIntent);
            }
        });

        Button testButton = findViewById(R.id.test_button);
        testButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                Intent testIntent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(testIntent);
            }
        });
    }



}
