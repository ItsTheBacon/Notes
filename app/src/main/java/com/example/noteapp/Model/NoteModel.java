package com.example.noteapp.Model;

import android.widget.TextView;

import java.io.Serializable;

public class NoteModel implements Serializable {
    private String txtTitle;

    public NoteModel(String txtTitle) {
        this.txtTitle = txtTitle;
    }

    public String getTxtTitle() {
        return txtTitle;
    }

    public void setTxtTitle(String txtTitle) {
        this.txtTitle = txtTitle;
    }
}
