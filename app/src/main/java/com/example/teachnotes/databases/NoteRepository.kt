package com.example.teachnotes.databases

class NoteRepository(private val dao: NoteDAO) {

    val notes = dao.getAllNotes()

    suspend fun insert(note: Note) {
        dao.insertNote(note)
    }

    suspend fun update(note: Note) {
        dao.updateNote(note)
    }

    suspend fun delete(note: Note) {
        dao.deleteNote(note)
    }

    suspend fun deleteAllNotes() {
        dao.deleteAllNotes()
    }

    suspend fun getNoteById(noteId: Int) {
        dao.getSingleNote(noteId)
    }

}