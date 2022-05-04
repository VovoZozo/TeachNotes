package com.example.teachnotes

import androidx.fragment.app.Fragment

interface Navigator {
    fun goBack()
    fun navigateToSettingsScreen()
    fun navigateToEditNoteScreen(noteId: Long)
    fun navigateToCreateNoteScreen()
}

fun Fragment.navigator(): Navigator = (requireActivity() as Navigator)