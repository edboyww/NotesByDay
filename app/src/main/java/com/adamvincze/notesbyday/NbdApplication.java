package com.adamvincze.notesbyday;

import android.app.Application;
import android.content.Context;

import org.joda.time.LocalDate;

import java.lang.ref.WeakReference;

/**
 * Application override class to handle the global state of the chosen date in the Helpers class (for now)
 */
public class NbdApplication extends Application {

    //Global Context for the sake of resolving resource strings in my helper class because fuck Android ecosystem overall at this point
    //(Seriously, how fucked up is this? https://stackoverflow.com/questions/4391720/how-can-i-get-a-resource-content-from-a-static-context )
    //Using a WeakReference to avoid memory leak
    private static WeakReference<Context> nbdContext;

    @Override
    public void onCreate() {
        super.onCreate();
        nbdContext = new WeakReference<>(this);
    }

    //Getter of the Context, because fuck this
    public static Context getNbdContext() { return nbdContext.get(); }

    //Statics for note intents
    public static final int NEW_NOTE_INTENT = 11;
    public static final int EDIT_NOTE_INTENT = 12;
    public static final int EMPTY_NOTE_RESULT = 111;

}
