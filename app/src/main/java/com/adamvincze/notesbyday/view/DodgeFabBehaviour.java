package com.adamvincze.notesbyday.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

/**
 * Behaviour class for dodging the Snackbar by the FAB
 */
public class DodgeFabBehaviour extends FloatingActionButton.Behavior {

    public DodgeFabBehaviour() {
        super();
    }

    public DodgeFabBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean getInsetDodgeRect(
            @NonNull CoordinatorLayout parent,
            @NonNull FloatingActionButton child,
            @NonNull Rect rect) {
        return false;
    }

}