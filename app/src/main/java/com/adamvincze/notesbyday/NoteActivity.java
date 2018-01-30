package com.adamvincze.notesbyday;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //Setting the text of the date chip with the date or hide it if the date is nonexistent
        Bundle fromMain = getIntent().getExtras();
        TextView dateChip = findViewById(R.id.date_chip_view);
        if (fromMain != null) {
            Calendar theDate = (Calendar) fromMain.getSerializable("date");
            dateChip.setText(NotesByDayHelper.nbdFormatCalendar(theDate));
        } else dateChip.setVisibility(View.GONE);



    }
}
