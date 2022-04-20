package com.example.teachnotes.fragments

import androidx.fragment.app.Fragment
import com.example.teachnotes.databases.Note

interface Navigator {
    fun navigateToEditNoteScreen(note: Note)
    fun navigateToCreateNoteScreen()
    fun navigateToNotesListScreenFromBackStack()
    fun navigateToSettingsScreen()
}

fun Fragment.navigator(): Navigator = (requireActivity() as Navigator)