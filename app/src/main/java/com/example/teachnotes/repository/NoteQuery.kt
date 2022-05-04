package com.example.teachnotes.repository

enum class NoteSortOrder {
    NONE,
    CREATED_DATE,
    CHANGED_DATE
}

data class NoteQuery(
    val sortOrder: NoteSortOrder = NoteSortOrder.NONE,
    val searchQuery: String? = null
) {
    fun hasSearchQuery() = !searchQuery.isNullOrBlank()
}