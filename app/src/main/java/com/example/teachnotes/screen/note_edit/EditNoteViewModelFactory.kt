package com.example.teachnotes.screen.note_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.teachnotes.repository.NoteRepository

class EditNoteViewModelFactory(
    private val noteId: Long,
    private val repository: NoteRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditNoteViewModel::class.java)) {
            return EditNoteViewModel(noteId, repository) as T
        }
        throw IllegalArgumentException(NOTE_DETAILS_FACTORY_EXCEPTION)
    }

    companion object {
        private const val NOTE_DETAILS_FACTORY_EXCEPTION = "Unknown View Model class"
    }
}
