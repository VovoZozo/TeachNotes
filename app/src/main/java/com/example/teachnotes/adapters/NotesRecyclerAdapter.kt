package com.example.teachnotes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teachnotes.R
import com.example.teachnotes.databases.Note
import com.example.teachnotes.databinding.NoteCardViewBinding

class NotesRecyclerAdapter(
    private val clickListener: NoteClickListener
) : ListAdapter<Note, NotesRecyclerAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: NoteCardViewBinding = DataBindingUtil
            .inflate(layoutInflater, R.layout.note_card_view, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setData(notesList: List<Note>) {
        submitList(notesList.toMutableList())
    }

    inner class MyViewHolder(private val binding: NoteCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                    val note = getItem(absoluteAdapterPosition)
                    clickListener.onUserClicked(note)
                }
            }
        }

        fun bind(note: Note) {
            binding.previewNoteTitle.text = note.noteTitle
            binding.previewNoteText.text = note.noteText
        }
    }

    interface NoteClickListener {
        fun onUserClicked(note: Note)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Note> = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.noteId == newItem.noteId
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }
        }
    }
}
