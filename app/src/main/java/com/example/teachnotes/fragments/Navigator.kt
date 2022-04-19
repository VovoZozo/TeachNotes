package com.example.teachnotes.fragments

import androidx.fragment.app.Fragment

interface Navigator {
    fun navigateToEditNoteScreen()
    fun navigateToNotesListScreenFromBackStack()
    fun navigateToSettingsScreen()
}

fun Fragment.navigator(): Navigator = (requireActivity() as Navigator)