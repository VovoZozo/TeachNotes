package com.example.teachnotes.repository

import com.example.teachnotes.databases.NoteDAO
import java.util.concurrent.ExecutorService

class NotesListRepository(
    private val noteDao: NoteDAO,
    private val ioExecutor: ExecutorService
) {

    fun getAllNotes() = noteDao.getAllNotes()

    fun deleteAllNotes() {
        ioExecutor.execute {
            noteDao.deleteAllNotes()
        }
    }
}