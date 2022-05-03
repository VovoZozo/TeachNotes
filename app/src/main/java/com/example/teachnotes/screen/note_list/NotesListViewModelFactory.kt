package com.example.teachnotes.screen.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.teachnotes.repository.NotesListRepository


class NotesListViewModelFactory(
    private val repository: NotesListRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesListViewModel::class.java)) {
            return NotesListViewModel(repository) as T
        }
        throw IllegalArgumentException(NOTES_LIST_FACTORY_EXCEPTION)
    }

    companion object {
        private const val NOTES_LIST_FACTORY_EXCEPTION = "Unknown View Model class"
    }
}