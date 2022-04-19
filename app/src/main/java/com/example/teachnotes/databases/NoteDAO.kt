package com.example.teachnotes.databases

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDAO {

    @Insert
    suspend fun insertNote(note: Note): Long

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM notes_data_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM notes_data_table")
    fun getAllNotes(): LiveData<List<Note>>


}