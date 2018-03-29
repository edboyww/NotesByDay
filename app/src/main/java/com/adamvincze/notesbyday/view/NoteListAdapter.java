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
 * RecyclerView Adapter for the noteData list
 */
class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private List<Note> noteList;

    NoteListAdapter(List<Note> list) {
        this.noteList = list;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.note_text)
        TextView noteTextView;
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
        Note note = noteList.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.noteTextView;
        textView.setText(note.getText());
        viewHolder.itemView.setClickable(true);
        viewHolder.itemView.setFocusable(true);
        viewHolder.itemView.setLongClickable(true);
    }

    /**
     * Returns the total count of items in the list
     * @return the number of the items in the view
     */
    @Override
    public int getItemCount() {
        try { return noteList.size(); }
        catch (NullPointerException npe) { return 0; }
    }

    /**
     * Update the Note List in the adapter
     * @param newList: the new Note List object
     */
    void updateList(List<Note> newList) {
        this.noteList = newList;
        notifyDataSetChanged();
    }

    Note getItem(int position) {
        return noteList.get(position);
    }

}
