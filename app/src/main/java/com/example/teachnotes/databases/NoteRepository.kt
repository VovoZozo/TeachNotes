package com.example.teachnotes.databases

import com.example.teachnotes.models.Note
import com.github.javafaker.Faker

object NoteRepository {
    private val noteFaker = Faker()
    var notes = mutableListOf<Note>()

    init {
        repeat(3) {
            addRandomNote()
        }
    }

    private fun addRandomNote() {
        notes.add(createRandomNote())
    }

    private fun createRandomNote() = Note(
        noteFaker.harryPotter().character(),
        noteFaker.harryPotter().quote()
    )
}