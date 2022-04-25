package com.example.teachnotes.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teachnotes.databases.Note
import com.example.teachnotes.databases.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val notes = repository.notes

    private lateinit var noteToUpdateOrDelete: Note
    fun setNoteToUpdateOrDelete(note: Note) {
        noteToUpdateOrDelete = note
    }

    fun getNoteToUpdateOrDelete(): Note = noteToUpdateOrDelete
    private var positionForUpdateOrDelete: Int = -1
    fun setPositionForUpdateOrDelete(position: Int) {
        positionForUpdateOrDelete = position
    }

    fun getPositionForUpdateOrDelete(): Int = positionForUpdateOrDelete

    fun createNote(id: Int, title: String, text: String, isFavorite: Boolean = false): Note {
        return Note(id, title, text, isFavorite)
    }

    fun saveNote(title: String, text: String, isFavorite: Boolean = false) {
        insert(Note(noteId = 0, title, text, false))
    }

    fun updateNote(noteId: Int, title: String, text: String, isFavorite: Boolean = false) {
        update(createNote(noteId, title, text, isFavorite))
    }

    fun sortedByInputTextListNotes(query: String): List<Note> {
        return if (notes.value == null) {
            emptyList()
        } else {
            notes.value!!.filter {
                it.noteTitle.lowercase().contains(query.lowercase())
            }
        }
    }

    fun insert(note: Note) {
        viewModelScope.launch {
            repository.insert(note)
        }
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }

    fun clearAll() = viewModelScope.launch {
        repository.deleteAllNotes()
    }

}