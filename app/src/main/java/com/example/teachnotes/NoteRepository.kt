package com.example.teachnotes

object NoteRepository {
    val notes = mutableListOf<Note>()

    init {
        repeat(10) {
            addRandomNote()
        }
    }

    private fun addRandomNote() {
        notes.add(createRandomNote())
    }

    private fun createRandomNote() = Note(
        "sfghsfhghshgsshfhgh",
        "dfshghsdfghsdjhdhgjhghjhfgkghjhdfghghfhhjhfhjhkfjkfhjfffjk"
    )
}