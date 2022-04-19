package com.example.teachnotes.fragments

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teachnotes.R
import com.example.teachnotes.adapters.NotesRecyclerAdapter
import com.example.teachnotes.databases.Note
import com.example.teachnotes.databases.NoteDatabase
import com.example.teachnotes.databases.NoteRepository
import com.example.teachnotes.databinding.FragmentNotesListBinding
import com.example.teachnotes.models.NoteViewModel
import com.example.teachnotes.models.NoteViewModelFactory


class NotesListFragment : Fragment(R.layout.fragment_notes_list) {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var binding: FragmentNotesListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_notes_list,
            container, false
        )
        setHasOptionsMenu(true)
        updateNotes()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_menu_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    private fun updateNotes() {
        val dao = NoteDatabase.getInstance(requireContext()).noteDAO
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        noteViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]
        binding.myViewModel = noteViewModel
        binding.lifecycleOwner = this
        initRecyclerView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_list_delete_all -> noteViewModel.clearAllOrDeleteNote()
            R.id.menu_list_settings -> {
                navigator().navigateToSettingsScreen()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        displayNotesList()
    }

    private fun displayNotesList() {
        noteViewModel.notes.observe(viewLifecycleOwner, Observer { list ->
            binding.recyclerView.adapter = NotesRecyclerAdapter(list) {
                listItemClicked(it)
            }
        })
    }

    private fun listItemClicked(note: Note) {
        Toast.makeText(
            this.context, "selected title is ${note.noteTitle}", Toast.LENGTH_LONG
        ).show()
    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Notes Recycler"
        (requireActivity() as AppCompatActivity)
            .supportActionBar?.setBackgroundDrawable(
                ColorDrawable(ContextCompat.getColor(requireContext(), R.color.black))
            )
        binding.todosIcon.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.notes_container, TodosFragment())
                .addToBackStack("todos")
                .commit()
        }

        binding.addNoteItemFab.setOnClickListener {
            navigator().navigateToEditNoteScreen()
        }


//        val searchView = itemView.findViewById<SearchView>(R.id.searchView)
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                val searchView = itemView.findViewById<SearchView>(R.id.searchView)
//                searchView.clearFocus()
//                if (query != null) {
//                    val searchNotes = list.filter {
//                        it.noteTitle.lowercase().contains(query.lowercase())
//                    }
//                    if (searchNotes.isNotEmpty()) {
//                        binding.recyclerView.adapter =
//                            NotesRecyclerAdapter(searchNotes)
//                    } else binding.recyclerView.adapter =
//                        NotesRecyclerAdapter(NOTE_NOT_FOUND)
//                }
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                if (newText != null) {
//                    val searchNotes = notes.filter {
//                        it.noteTitle.lowercase().contains(newText.lowercase())
//                    }
//                    if (searchNotes.isNotEmpty()) {
//                        binding.recyclerView.adapter =
//                            NotesRecyclerAdapter(searchNotes)
//                    } else binding.recyclerView.adapter =
//                        NotesRecyclerAdapter(NOTE_NOT_FOUND)
//                }
//                return false
//            }
//        })
    }
}