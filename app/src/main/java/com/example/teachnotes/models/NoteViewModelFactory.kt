package com.example.teachnotes.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.teachnotes.databases.NoteRepository


class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException(NOTE_FACTORY_EXCEPTION)
    }

    companion object {
        private const val NOTE_FACTORY_EXCEPTION = "Unknown View Model class"
    }
}