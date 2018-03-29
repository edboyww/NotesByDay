package com.adamvincze.notesbyday.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * RecyclerView: Implementing single item click and long press
 *
 * - creating an Interface for single tap and long press
 * - Parameters are its respective view and its position
 * */

interface NoteClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}

/**
 * RecyclerView: Implementing single item click and long press (Part-II)
 *
 * - creating an innerclass implementing RecyclerView.OnItemTouchListener
 * - Pass clickListener interface as parameter
 * */

class NoteTouchListener implements RecyclerView.OnItemTouchListener {

    private NoteClickListener clickListener;
    private GestureDetector gestureDetector;

    NoteTouchListener(Context context, final RecyclerView recycleView, final NoteClickListener clickListener) {

        this.clickListener =clickListener;
        gestureDetector=new GestureDetector(
                context,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                        if (child!=null && clickListener!=null) {
                            clickListener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                        }
                    }
                }
        );
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child=rv.findChildViewUnder(e.getX(),e.getY());
        if (child!=null && clickListener !=null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child,rv.getChildAdapterPosition(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}


