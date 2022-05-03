package com.example.teachnotes.screen.note_edit

import androidx.lifecycle.ViewModel
import com.example.teachnotes.repository.NoteRepository

class EditNoteViewModel(
    private val noteId: Long,
    private val repository: NoteRepository
) : ViewModel() {

    var currentNote = repository.getNoteById(noteId)

    fun updateNote() {
        currentNote.value?.let { repository.update(it) }
    }

}