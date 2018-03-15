package com.adamvincze.notesbyday.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adamvincze.notesbyday.R;
import com.adamvincze.notesbyday.model.Note;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RecyclerView Adapter for the noteList list
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

    //Member variables for the noteList and the context
    private List<Note> noteList;


    // Pass in the contact array into the constructor
    NoteListAdapter(List<Note> notes) {
        this.noteList = notes;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override @NonNull
    public NoteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.note_item, parent, false);
        return new ViewHolder(contactView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Note note = noteList.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.noteTextView;
        textView.setText(note.getText());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        if (noteList != null) return noteList.size();
        else return 0;
    }

    void setNoteList(List<Note> notes) {
        this.noteList = notes;
        notifyDataSetChanged();
    }

}
