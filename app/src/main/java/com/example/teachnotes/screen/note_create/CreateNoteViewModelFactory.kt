package com.example.teachnotes.screen.note_create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.teachnotes.repository.NoteRepository

class CreateNoteViewModelFactory(
    private val repository: NoteRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateNoteViewModel::class.java)) {
            return CreateNoteViewModel(repository) as T
        }
        throw IllegalArgumentException(CREATE_NOTE_FACTORY_EXCEPTION)
    }

    companion object {
        private const val CREATE_NOTE_FACTORY_EXCEPTION = "Unknown View Model class"
    }
}