package com.example.teachnotes.screen.note_list

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teachnotes.AppExecutors
import com.example.teachnotes.R
import com.example.teachnotes.databases.Note
import com.example.teachnotes.databases.NoteDatabase
import com.example.teachnotes.databinding.FragmentNotesListBinding
import com.example.teachnotes.navigator
import com.example.teachnotes.repository.NoteSortOrder
import com.example.teachnotes.repository.NotesListRepository

class NotesListFragment : Fragment(R.layout.fragment_notes_list) {

    private lateinit var prefs: SharedPreferences
    private lateinit var viewModel: NotesListViewModel
    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NotesRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        prefs = requireContext().getSharedPreferences("TeachNotesSettings", Context.MODE_PRIVATE)
        initViewModel()
        initRecyclerView()
        return binding.root
    }

    private fun initViewModel() {
        val dao = NoteDatabase.getInstance(requireContext()).noteDAO
        val repository = NotesListRepository(dao, AppExecutors.ioExecutor)
        val factory = NotesListViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[NotesListViewModel::class.java]
    }

    private fun initRecyclerView() {
        setInitLayoutManager()
        adapter = NotesRecyclerAdapter(
            object : NotesRecyclerAdapter.NoteClickListener {
                override fun onNoteClicked(note: Note, position: Int) {
                    navigator().navigateToEditNoteScreen(note.noteId)
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
        viewModel.notes.observe(viewLifecycleOwner) { adapter.setData(it) }
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
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()


        val gridCheckBox = binding.gridCheckBox
        gridCheckBox.isChecked = prefs.getBoolean(APP_PREFERENCES_IS_GRID_ENABLE, false)
        gridCheckBox.setOnClickListener {
            if (gridCheckBox.isChecked) {
                binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                prefs.edit().putBoolean(APP_PREFERENCES_IS_GRID_ENABLE, true).apply()
            } else {
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                prefs.edit().putBoolean(APP_PREFERENCES_IS_GRID_ENABLE, false).apply()
            }
        }
        binding.addNoteItemFab.setOnClickListener {
            navigator().navigateToCreateNoteScreen()
        }

        binding.menuButton.setOnClickListener {
            val popup = PopupMenu(requireContext(), binding.menuButton)
            popup.inflate(R.menu.notes_menu_list)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_list_delete_all -> {
                        viewModel.onRemoveAllClicked()
                    }
                    R.id.menu_list_settings -> {
                        navigator().navigateToSettingsScreen()
                    }
                    R.id.menu_list_sort_date_created -> {
                        viewModel.setSortOrder(NoteSortOrder.CREATED_DATE)
                    }
                    R.id.menu_list_sort_date_edited -> {
                        viewModel.setSortOrder(NoteSortOrder.CHANGED_DATE)
                    }
                }
                return@setOnMenuItemClickListener true
            }
            popup.show()
        }

        viewModel.showListEmptyMessageErrorEvent.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.onSearchQueryChanged(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onSearchQueryChanged(newText.toString())
                return false
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val APP_PREFERENCES_IS_GRID_ENABLE = "APP_PREFERENCES_IS_GRID_ENABLE"
    }
}
