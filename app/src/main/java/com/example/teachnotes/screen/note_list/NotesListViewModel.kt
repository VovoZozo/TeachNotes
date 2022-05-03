package com.example.teachnotes.screen.note_list

import androidx.lifecycle.ViewModel
import com.example.teachnotes.databases.Note
import com.example.teachnotes.repository.NotesListRepository

class NotesListViewModel(private val repository: NotesListRepository) : ViewModel() {

    val notes = repository.getAllNotes()

    fun clearAll() = repository.deleteAllNotes()

    fun sortedByInputTextListNotes(query: String): List<Note> {
        return if (notes.value == null) {
            emptyList()
        } else {
            notes.value!!.filter {
                it.noteTitle.lowercase().contains(query.lowercase())
            }
        }
    }
}