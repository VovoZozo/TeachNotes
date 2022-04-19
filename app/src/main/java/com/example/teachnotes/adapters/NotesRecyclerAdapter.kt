package com.example.teachnotes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.teachnotes.R
import com.example.teachnotes.databases.Note
import com.example.teachnotes.databinding.NoteCardViewBinding

class NotesRecyclerAdapter(
    private val notes: List<Note>,
    private val clickListener: (Note) -> Unit
) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: NoteCardViewBinding = DataBindingUtil
            .inflate(layoutInflater, R.layout.note_card_view, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(notes[position], clickListener)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

}

class MyViewHolder(private val binding: NoteCardViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(note: Note, clickListener: (Note) -> Unit) {
        binding.previewNoteTitle.text = note.noteTitle
        binding.previewNoteText.text = note.noteText
        binding.cardNote.setOnClickListener {
            clickListener(note)
        }
    }

}
