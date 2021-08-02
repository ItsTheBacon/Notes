package com.example.noteapp.model;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class NoteModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;


    private String txtTitle;
    @ColumnInfo
    private String date;


    private String radiobac;

    public NoteModel(String txtTitle, String date, String radiobac) {
        this.txtTitle = txtTitle;
        this.date = date;
        this.radiobac = radiobac;
    }

    public String getRadiobac() {
        return radiobac;
    }

    public void setRadiobac(String radiobac) {
        this.radiobac = radiobac;
    }


    public NoteModel(String title) {
        this.txtTitle = title;
    }

    public String getTxtTitle() {
        return txtTitle;
    }

    public void setTxtTitle(String txtTitle) {
        this.txtTitle = txtTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
