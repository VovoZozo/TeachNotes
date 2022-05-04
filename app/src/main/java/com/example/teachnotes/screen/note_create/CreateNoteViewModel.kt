package com.example.teachnotes.screen.note_create

import androidx.lifecycle.ViewModel
import com.example.teachnotes.databases.Note
import com.example.teachnotes.repository.NoteRepository
import java.text.DateFormat
import java.util.*

class CreateNoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    var isNoteFavorite = false

    fun saveNote(noteTitle: String, noteText: String) {
        val creationDate = DateFormat.getDateTimeInstance().format(Date())
        repository.insert(Note(0, noteTitle, noteText, isNoteFavorite, creationDate, creationDate))
    }

    fun onIsNoteFavoriteClick() {
        isNoteFavorite = !isNoteFavorite
    }
}