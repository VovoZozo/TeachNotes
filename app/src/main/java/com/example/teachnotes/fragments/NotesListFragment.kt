package com.example.teachnotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teachnotes.R
import com.example.teachnotes.adapters.NotesRecyclerAdapter
import com.example.teachnotes.databases.NoteRepository
import com.example.teachnotes.databinding.FragmentNotesListBinding
import com.example.teachnotes.models.Note


class NotesListFragment : Fragment(R.layout.fragment_notes_list) {

    private var _binding: FragmentNotesListBinding? = null
    private val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentNotesListBinding.inflate(inflater, container, false).also {
            this._binding = it
        }.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        var notes = NoteRepository.notes
        binding.recyclerView.adapter = NotesRecyclerAdapter(notes)

        binding.todosIcon.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.notes_container, TodosFragment())
                .addToBackStack("todos")
                .commit()
        }

        binding.addNoteItemFab.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.notes_container, FragmentEditNote())
                .addToBackStack("show note")
                .commit()
        }


        val searchView = itemView.findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchView = itemView.findViewById<SearchView>(R.id.searchView)
                searchView.clearFocus()
                if (query != null) {
                    val searchNotes = notes.filter {
                        it.noteTitle.lowercase().contains(query.lowercase())
                    }
                    if (searchNotes.isNotEmpty()) {
                        binding.recyclerView.adapter =
                            NotesRecyclerAdapter(searchNotes)
                    } else binding.recyclerView.adapter =
                        NotesRecyclerAdapter(NOTE_NOT_FOUND)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    val searchNotes = notes.filter {
                        it.noteTitle.lowercase().contains(newText.lowercase())
                    }
                    if (searchNotes.isNotEmpty()) {
                        binding.recyclerView.adapter =
                            NotesRecyclerAdapter(searchNotes)
                    } else binding.recyclerView.adapter =
                        NotesRecyclerAdapter(NOTE_NOT_FOUND)
                }
                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }

    companion object {
        private val NOTE_NOT_FOUND = listOf<Note>(Note("Not", "Found"))
    }
}