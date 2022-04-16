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
                binding.recyclerView.adapter =
                    NotesRecyclerAdapter(notes.filter { it.noteTitle.contains(query!!) })
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.recyclerView.adapter =
                    NotesRecyclerAdapter(notes.filter { it.noteTitle.contains(newText!!) })
                return false
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
}