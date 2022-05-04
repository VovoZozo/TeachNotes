package com.example.teachnotes.screen.note_edit

import androidx.lifecycle.ViewModel
import com.example.teachnotes.repository.NoteRepository
import java.text.DateFormat
import java.util.*

class EditNoteViewModel(
    private val noteId: Long,
    private val repository: NoteRepository
) : ViewModel() {

    var currentNote = repository.getNoteById(noteId)

    fun updateNote() {
        val changeDate = DateFormat.getDateTimeInstance().format(Date())
        currentNote.value?.let {
            it.dateChange = changeDate
            repository.update(it)
        }
    }

    fun onIsNoteFavoriteClick() {
        currentNote.value?.isFavorite = !currentNote.value?.isFavorite!!
    }
}