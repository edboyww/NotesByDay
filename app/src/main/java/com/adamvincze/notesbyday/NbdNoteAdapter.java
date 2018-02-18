package com.adamvincze.notesbyday;

//RecyclerView Adapter for the notes list

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class NbdNoteAdapter extends RecyclerView.Adapter<NbdNoteAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteTextView;
        ViewHolder(View itemView) {
            super(itemView);
            noteTextView = itemView.findViewById(R.id.note_text);
        }
    }

    //Member variables for the notes and the context
    private List<NbdNote> mNotes;
    private Context mContext;

    // Pass in the contact array into the constructor
    NbdNoteAdapter(Context context, List<NbdNote> notes) {
        mNotes = notes;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() { return mContext; }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public NbdNoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.note_item, parent, false);
        return new ViewHolder(contactView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(NbdNoteAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        NbdNote note = mNotes.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.noteTextView;
        textView.setText(note.getText());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mNotes.size();
    }

}
