package com.example.teachnotes.screen.note_create

import androidx.lifecycle.ViewModel
import com.example.teachnotes.databases.Note
import com.example.teachnotes.repository.NoteRepository

class CreateNoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    fun saveNote(noteTitle: String, noteText: String, isFavorite: Boolean = false) {

        repository.insert(Note(0, noteTitle, noteText, isFavorite))
    }


}