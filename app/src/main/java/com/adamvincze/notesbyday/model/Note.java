package com.adamvincze.notesbyday.model;

//The data class for the note instance

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.io.Serializable;

import lombok.Data;

@Entity
@Data
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private LocalDate date;

    private String text;

    private LocalDateTime added;

    private LocalDateTime edited;

}
