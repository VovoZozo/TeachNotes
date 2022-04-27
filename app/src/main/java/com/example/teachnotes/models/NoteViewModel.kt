package com.example.teachnotes.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teachnotes.databases.Note
import com.example.teachnotes.databases.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val notes = repository.notes
    var editedNote: Note = DEFAULT_NOTE

    fun saveNote() {
        if (editedNote.noteId != DEFAULT_NOTE.noteId) {
            update(editedNote)
        } else {
            insert(editedNote)
        }
        editedNote = DEFAULT_NOTE
    }

    fun changeFavoriteState() {
        editedNote.isFavorite = !editedNote.isFavorite
    }

    fun initEditedNote(note: Note) {
        editedNote = notes.value?.find { it.noteId == note.noteId } ?: DEFAULT_NOTE
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

    companion object {
        private const val DEFAULT_NOTE_ID = 0
        private const val EMPTY_TEXT = ""
        private val DEFAULT_NOTE = Note(DEFAULT_NOTE_ID, EMPTY_TEXT, EMPTY_TEXT, false)
    }
}


//    private lateinit var noteToUpdateOrDelete: Note


//    fun setNoteToUpdateOrDelete(note: Note) {
//        noteToUpdateOrDelete = note
//    }
//
//    fun getNoteToUpdateOrDelete(): Note = noteToUpdateOrDelete
//    private var positionForUpdateOrDelete: Int = -1
//    fun setPositionForUpdateOrDelete(position: Int) {
//        positionForUpdateOrDelete = position
//    }
//
//    fun getPositionForUpdateOrDelete(): Int = positionForUpdateOrDelete
//
//    fun createNote(id: Int, title: String, text: String, isFavorite: Boolean = false): Note {
//        return Note(id, title, text, isFavorite)
//    }
//


//    fun updateNote(noteId: Int, title: String, text: String, isFavorite: Boolean = false) {
//        var note = Note(noteId, title, text, isFavorite)
//        update(note)
//    }


//    fun getSingleNote(noteId: Int) {
//        getSingleNote(noteId)
//    }