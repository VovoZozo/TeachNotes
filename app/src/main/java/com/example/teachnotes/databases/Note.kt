package com.example.teachnotes.databases
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes_data_table")
data class Note(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    var noteId: Int,

    @ColumnInfo(name = "note_title")
    var noteTitle: String,

    @ColumnInfo(name = "note_text")
    var noteText: String,

    @ColumnInfo(name = "note_is_favorite")
    var isFavorite: Boolean
) : Serializable
