package com.example.teachnotes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.teachnotes.R
import com.example.teachnotes.databases.Note
import com.example.teachnotes.databinding.NoteCardViewBinding

class NotesRecyclerAdapter : RecyclerView.Adapter<MyViewHolder>() {

    private var notes: List<Note> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: NoteCardViewBinding = DataBindingUtil
            .inflate(layoutInflater, R.layout.note_card_view, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setData(notesList: List<Note>) {
        notes = notesList
        notifyDataSetChanged()
    }
}


class MyViewHolder(private val binding: NoteCardViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(note: Note) {
        binding.previewNoteTitle.text = note.noteTitle
        binding.previewNoteText.text = note.noteText
    }
}
