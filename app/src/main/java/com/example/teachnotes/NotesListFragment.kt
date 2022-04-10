package com.example.teachnotes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotesListFragment : Fragment(R.layout.fragment_notes_list) {

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val recyclerview = itemView.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerview.layoutManager = LinearLayoutManager(activity)
        var notes = NoteRepository.notes
        recyclerview.adapter = RecyclerAdapter(notes)
    }
}