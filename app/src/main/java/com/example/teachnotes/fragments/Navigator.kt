package com.example.teachnotes.fragments

import androidx.fragment.app.Fragment
import com.example.teachnotes.databases.Note

interface Navigator {
    fun navigateToNotesListScreen()
    fun navigateToSettingsScreen()
    fun navigateToEditNoteScreen(note: Note)
    fun navigateToCreateNoteScreen()
    fun navigateToTodosScreen()
    fun navigateUp()
}

fun Fragment.navigator(): Navigator = (requireActivity() as Navigator)