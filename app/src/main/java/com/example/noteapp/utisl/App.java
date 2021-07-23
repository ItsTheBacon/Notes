package com.example.noteapp.utisl;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.noteapp.room.NoteDatabase;

public class App extends Application {

    public static NoteDatabase instance = null;
    public static Context context;

    public static NoteDatabase getInstance() {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    NoteDatabase.class, "note.database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initSP();
        getInstance();
    }

    private void initSP() {
        PreferncesHelper preferncesHelper = new PreferncesHelper();
        preferncesHelper.init(this);

    }

}
