package com.adamvincze.notesbyday;

import android.app.Application;

import org.joda.time.LocalDate;

//Application override class to handle the global state of the chosen date
public class NbdApplication extends Application {

    //The static variable for storing the actual chosen date
    private static LocalDate nbdDate = new LocalDate();

    @Override
    public void onCreate() {
        super.onCreate();
        nbdDate = LocalDate.now(); //Initializing the date
    }

    //Getter
    public static LocalDate getNbdDate() {
        return nbdDate;
    }

    //Setter
    public static void setNbdDate(LocalDate date) {
        nbdDate = date;
    }

}
