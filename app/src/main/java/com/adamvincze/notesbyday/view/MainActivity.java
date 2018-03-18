package com.adamvincze.notesbyday.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.adamvincze.notesbyday.NbdApplication;
import com.adamvincze.notesbyday.R;
import com.adamvincze.notesbyday.model.Note;
import com.adamvincze.notesbyday.viewmodel.MainActivityViewModel;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.adamvincze.notesbyday.Helpers.formatDate;

/**
 * The main activity - view
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_toolbar) Toolbar mainToolbar;
    @BindView(R.id.main_date_textview) TextView currentDateView;
    @BindView(R.id.main_previous_day_button) ImageButton previousButton;
    @BindView(R.id.main_next_day_button) ImageButton nextButton;
    @BindView(R.id.main_new_note_fab) FloatingActionButton newNoteFab;
    @BindView(R.id.main_list_view) RecyclerView mainListView;

    private MainActivityViewModel viewModel;
    private List<Note> noteListByDate;
    private NoteListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //putting the toolbar on top in the support library
        setSupportActionBar(mainToolbar);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        viewModel.setDate(new LocalDate());

        //initially show the current date at the open of the app
        currentDateView.setText(formatDate(viewModel.getSelectedDate()));

        adapter = new NoteListAdapter();
        mainListView.setAdapter(adapter);
        mainListView.setLayoutManager(new LinearLayoutManager(this));
        viewModel.refreshNotesDataByDate().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                // Update the cached copy of the words in the adapter.
                adapter.setNoteData(notes);
            }
        });

    }

    //listener of the previous day button on the note card
    @OnClick(R.id.main_previous_day_button)
    public void previousDay() {
        viewModel.setDate(viewModel.getSelectedDate().minusDays(1));
        currentDateView.setText(formatDate(viewModel.getSelectedDate()));
    }

    //listener of the next day button on the note card
    @OnClick(R.id.main_next_day_button)
    public void nextDay() {
        viewModel.setDate(viewModel.getSelectedDate().plusDays(1));
        currentDateView.setText(formatDate(viewModel.getSelectedDate()));
    }

    //listener of the New note FAB, passing the current date
    @OnClick(R.id.main_new_note_fab)
    public void newNote() {
        Intent newNoteIntent = new Intent(MainActivity.this, NoteActivity.class);
        Note newNote = new Note();
        newNote.setDate(viewModel.getSelectedDate());
        newNote.setAdded(new LocalDateTime());
        newNoteIntent.putExtra("note", newNote);
        startActivityForResult(newNoteIntent, NbdApplication.NEW_NOTE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent fromNewNote) {
        switch (requestCode) {
            case NbdApplication.NEW_NOTE:
                switch (resultCode) {
                    case RESULT_OK:
                        Note newNote = (Note) fromNewNote.getSerializableExtra("note");
                        viewModel.setDate(newNote.getDate());
                        currentDateView.setText(formatDate(viewModel.getSelectedDate()));
                        viewModel.putNote(newNote);
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

    /**
     * RecyclerView Adapter for the noteData list
     */
    public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

        // Provide a direct reference to each of the views within a data item
        // Used to cache the views within the item layout for fast access
        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.note_text) TextView noteTextView;
            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        // Usually involves inflating a layout from XML and returning the holder
        @Override @NonNull
        public NoteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View contactView = inflater.inflate(R.layout.note_item, parent, false);
            return new NoteListAdapter.ViewHolder(contactView);
        }

        // Involves populating data into the item through holder
        @Override
        public void onBindViewHolder(@NonNull NoteListAdapter.ViewHolder viewHolder, int position) {
            // Get the data model based on position
            @SuppressWarnings("ConstantConditions") Note note = noteListByDate.get(position);

            // Set item views based on your views and data model
            TextView textView = viewHolder.noteTextView;
            textView.setText(note.getText());
        }

        // Returns the total count of items in the list
        @Override
        public int getItemCount() {
            try { //noinspection ConstantConditions
                return noteListByDate.size(); }
            catch (NullPointerException npe) { return 0; }
        }

        //TODO: separate notify...() events
        void setNoteData(List<Note> newNoteList) {
            noteListByDate = newNoteList;
            notifyDataSetChanged();
        }

    }

}
