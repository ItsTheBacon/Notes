package com.example.noteapp.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.noteapp.model.NoteModel;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notemodel")
    LiveData<List<NoteModel>> getAll();


    @Insert
    void insertNote(NoteModel noteModel);

    @Delete
    void delete(NoteModel model);

    @Update
    void update(NoteModel model);

}