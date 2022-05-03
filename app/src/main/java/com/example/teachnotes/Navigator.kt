package com.example.teachnotes

import androidx.fragment.app.Fragment

interface Navigator {
    fun navigateToNotesListScreen()
    fun navigateToSettingsScreen()
    fun navigateToEditNoteScreen(noteId: Long)
    fun navigateToCreateNoteScreen()
    fun navigateUp()
}

fun Fragment.navigator(): Navigator = (requireActivity() as Navigator)