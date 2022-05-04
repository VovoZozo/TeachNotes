package com.example.teachnotes.databases

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface NoteDAO {

    @Insert
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("DELETE FROM notes_data_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM notes_data_table")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_data_table WHERE note_id=:noteId")
    fun getSingleNote(noteId: Long): LiveData<Note>

    @RawQuery(observedEntities = [Note::class])
    fun getNotesByQuery(query: SupportSQLiteQuery): LiveData<List<Note>>

}