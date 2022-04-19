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


//    private val noteFaker = Faker()
//    var notes = mutableListOf<Note>()
//
//    init {
//        repeat(3) {
//            addRandomNote()
//        }
//    }
//
//    private fun addRandomNote() {
//        notes.add(createRandomNote())
//    }
//
//    private fun createRandomNote() = Note(
//        noteFaker.harryPotter().character(),
//        noteFaker.harryPotter().quote()
//    )
}