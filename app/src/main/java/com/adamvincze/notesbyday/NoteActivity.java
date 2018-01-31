package com.adamvincze.notesbyday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.joda.time.LocalDate;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //Setting the text of the date chip with the date or hide it if the date is nonexistent
        Bundle fromMain = getIntent().getExtras();
        TextView dateChip = findViewById(R.id.date_chip_view);
        if (fromMain != null) {
            LocalDate theDate = (LocalDate) fromMain.getSerializable("date");
            dateChip.setText(NotesByDayHelper.nbdFormatDate(theDate));
        } else dateChip.setVisibility(View.GONE);



    }
}
