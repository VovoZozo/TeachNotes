package com.example.teachnotes.repository

import com.example.teachnotes.databases.Note
import com.example.teachnotes.databases.NoteDAO
import java.util.concurrent.ExecutorService

class NoteRepository(
    private val noteDao: NoteDAO,
    private val ioExecutor: ExecutorService
) {
    fun getNoteById(noteId: Long) = noteDao.getSingleNote(noteId)

    fun insert(note: Note) {
        ioExecutor.execute {
            noteDao.insertNote(note)
        }
    }

    fun update(note: Note) {
        ioExecutor.execute {
            noteDao.updateNote(note)
        }
    }

    fun delete(note: Note) {
        ioExecutor.execute {
            noteDao.deleteNote(note)
        }
    }
}