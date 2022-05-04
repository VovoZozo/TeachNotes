package com.example.teachnotes.screen.note_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.teachnotes.DataEvent
import com.example.teachnotes.databases.Note
import com.example.teachnotes.repository.NoteQuery
import com.example.teachnotes.repository.NoteSortOrder
import com.example.teachnotes.repository.NotesListRepository

class NotesListViewModel(private val repository: NotesListRepository) : ViewModel() {

    private val _showListEmptyMessageErrorEvent = MutableLiveData<DataEvent<String>>()
    val showListEmptyMessageErrorEvent: LiveData<DataEvent<String>> =
        _showListEmptyMessageErrorEvent

    fun onRemoveAllClicked() {
        if (notes.value.isNullOrEmpty()) {
            _showListEmptyMessageErrorEvent.value = DataEvent(EMPTY_LIST_ERROR_MESSAGE)
            return
        }
        repository.deleteAllNotes()
    }

    private var noteQuery = NoteQuery()
    private val noteQueryLiveData = MutableLiveData(noteQuery)

    val notes: LiveData<List<Note>> = noteQueryLiveData.switchMap {
        repository.getAllNotes(it)
    }

    fun setSortOrder(sortOrder: NoteSortOrder) {
        noteQuery = noteQuery.copy(sortOrder = sortOrder)
        noteQueryLiveData.value = noteQuery
    }

    fun onSearchQueryChanged(query: String) {
        noteQuery = noteQuery.copy(searchQuery = query)
        noteQueryLiveData.value = noteQuery
    }

    companion object {
        private const val EMPTY_LIST_ERROR_MESSAGE = "The list is already empty"
    }
}