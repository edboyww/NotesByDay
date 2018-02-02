package com.adamvincze.notesbyday;

import android.app.Application;
import android.content.Context;

import org.joda.time.LocalDate;

import java.lang.ref.WeakReference;

//Application override class to handle the global state of the chosen date
public class NbdApplication extends Application {

    //The static variable for storing the actual chosen date
    private static LocalDate nbdDate = new LocalDate();

    //Global Context for the sake of resolving resource strings in my helper class because fuck Android ecosystem overall at this point
    //( Seriously, how fucked up is this? https://stackoverflow.com/questions/4391720/how-can-i-get-a-resource-content-from-a-static-context )
    //Using a WeakReference to avoid memory leak
    private static WeakReference<Context> nbdContext;

    @Override
    public void onCreate() {
        super.onCreate();
        nbdDate = LocalDate.now(); //Initializing the date
        nbdContext = new WeakReference<Context>(this);
    }

    //Getter of the date
    public static LocalDate getNbdDate() {
        return nbdDate;
    }

    //Setter of the date
    public static void setNbdDate(LocalDate date) {
        nbdDate = date;
    }

    //Getter of the Context, because fuck this
    public static Context getNbdContext() { return nbdContext.get(); }

}
