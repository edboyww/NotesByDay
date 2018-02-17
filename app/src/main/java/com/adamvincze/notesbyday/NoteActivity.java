package com.adamvincze.notesbyday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //Setting the text of the date chip with the date or hide it if the date is nonexistent
        TextView dateChip = findViewById(R.id.date_chip_view);
        dateChip.setText(NbdHelper.formatDate(NbdApplication.getNbdDate()));


    }
}
