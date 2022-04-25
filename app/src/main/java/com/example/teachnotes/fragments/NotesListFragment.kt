package com.example.teachnotes.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
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

    private lateinit var prefs: SharedPreferences
    private lateinit var noteViewModel: NoteViewModel
    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NotesRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        prefs = requireContext().getSharedPreferences("TeachNotesSettings", Context.MODE_PRIVATE)
        updateNotes()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    private fun updateNotes() {
        val dao = NoteDatabase.getInstance(requireContext()).noteDAO
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        noteViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]
        initRecyclerView()
    }


    private fun initRecyclerView() {
        setInitLayoutManager()
        adapter = NotesRecyclerAdapter(object : NotesRecyclerAdapter.NoteClickListener {
            override fun onNoteClicked(note: Note, position: Int) {
                navigator().navigateToEditNoteScreen(note)
            }
        },
            object : NotesRecyclerAdapter.NoteLongClickListener {
                override fun onNoteChecked(note: Note) {}
                override fun onNoteDeChecked(note: Note) {}
            })
        binding.recyclerView.adapter = adapter
        displayNotesList()
    }


    private fun displayNotesList() {
        noteViewModel.notes.observe(viewLifecycleOwner) { adapter.setData(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_menu_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_list_delete_all -> {
                noteViewModel.clearAll()
            }
            R.id.menu_list_settings -> {
                navigator().navigateToSettingsScreen()
            }
            R.id.enable_grid -> {
                binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                prefs.edit().putBoolean(APP_PREFERENCES_IS_GRID_ENABLE, true).apply()
            }
            R.id.disable_grid -> {
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                prefs.edit().putBoolean(APP_PREFERENCES_IS_GRID_ENABLE, false).apply()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setInitLayoutManager() {
        if (prefs.getBoolean(APP_PREFERENCES_IS_GRID_ENABLE, false)) {
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        } else {
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            this.title = null
        }

        binding.addNoteItemFab.setOnClickListener {
            navigator().navigateToCreateNoteScreen()
        }


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                if (query != null) {
                    val searchNotes = noteViewModel.sortedByInputTextListNotes(query)
                    adapter.setData(searchNotes)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    val searchNotes = noteViewModel.sortedByInputTextListNotes(newText)
                    adapter.setData(searchNotes)
                }
                return false
            }
        })
    }

    companion object {
        private const val APP_PREFERENCES_IS_GRID_ENABLE = "APP_PREFERENCES_IS_GRID_ENABLE"
    }
}
