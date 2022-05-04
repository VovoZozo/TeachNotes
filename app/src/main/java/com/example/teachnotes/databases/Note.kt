package com.example.teachnotes.databases
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes_data_table")
data class Note(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    var noteId: Long,

    @ColumnInfo(name = "note_title")
    var noteTitle: String,

    @ColumnInfo(name = "note_text")
    var noteText: String,

    @ColumnInfo(name = "note_is_favorite")
    var isFavorite: Boolean,

    @ColumnInfo(name = "note_date_create")
    var dateCreate: String,

    @ColumnInfo(name = "note_date_change")
    var dateChange: String

) : Parcelable
