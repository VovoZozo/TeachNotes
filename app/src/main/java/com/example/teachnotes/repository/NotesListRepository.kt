package com.example.teachnotes.repository

import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.teachnotes.databases.Note
import com.example.teachnotes.databases.NoteDAO
import java.util.concurrent.ExecutorService

class NotesListRepository(
    private val noteDao: NoteDAO,
    private val ioExecutor: ExecutorService
) {

    fun getAllNotes(noteQuery: NoteQuery): LiveData<List<Note>> {
        val sortQueryText = when (noteQuery.sortOrder) {
            NoteSortOrder.NONE -> ""
            NoteSortOrder.CREATED_DATE -> "ORDER BY note_date_create DESC"
            NoteSortOrder.CHANGED_DATE -> "ORDER BY note_date_change DESC"
        }

        val params = mutableListOf<Any?>()
        val searchQueryText = if (noteQuery.hasSearchQuery()) {
            "WHERE note_title LIKE '%' || ? || '%'"
        } else ""

        if (noteQuery.hasSearchQuery()) {
            params.add(noteQuery.searchQuery)
        }

        val queryText = "SELECT * FROM notes_data_table $searchQueryText $sortQueryText"

        val query = SimpleSQLiteQuery(
            queryText,
            params.toTypedArray()
        )
        return noteDao.getNotesByQuery(query)
    }

    fun deleteAllNotes() {
        ioExecutor.execute {
            noteDao.deleteAllNotes()
        }
    }
}