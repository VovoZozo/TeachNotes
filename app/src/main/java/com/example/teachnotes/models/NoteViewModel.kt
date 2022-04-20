package com.example.teachnotes.models

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teachnotes.databases.Note
import com.example.teachnotes.databases.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel(), Observable {

    val notes = repository.notes
    private var isUpdateOrDelete = false
    private lateinit var noteToUpdateOrDelete: Note

    @Bindable
    val inputTitle = MutableLiveData<String?>()

    @Bindable
    val inputText = MutableLiveData<String?>()

    fun saveOrUpdateNote() {
        val title = inputTitle.value!!
        val text = inputText.value!!
        insert(Note(noteId = 0, title, text, false))
        inputTitle.value = null
        inputText.value = null
    }

    fun clearAllOrDeleteNote() {
        clearAll()
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

    private fun insert(note: Note) {
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

    private fun clearAll() = viewModelScope.launch {
        repository.deleteAllNotes()
    }

    fun initUpdateAndDelete(note: Note) {
        inputTitle.value = note.noteTitle
        inputText.value = note.noteText
        isUpdateOrDelete = true
        noteToUpdateOrDelete = note
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}