package com.example.teachnotes.fragments

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
    private lateinit var adapter: NotesRecyclerAdapter

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

    private fun updateNotes() {
        val dao = NoteDatabase.getInstance(requireContext()).noteDAO
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        noteViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]
        binding.myViewModel = noteViewModel
        binding.lifecycleOwner = this
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = NotesRecyclerAdapter(object : NotesRecyclerAdapter.NoteClickListener {
            override fun onNoteClicked(note: Note) {
                navigator().navigateToEditNoteScreen(note)
            }
        },
            object : NotesRecyclerAdapter.NoteLongClickListener {
                override fun onNoteChecked(note: Note) {
                    noteViewModel.setCheckedNote(note)
                    changeMainBottomBarToSelectBar()
                    Log.i("cc", "${noteViewModel.checkedNotes.size}")
                }

                override fun onNoteDeChecked(note: Note) {
                    noteViewModel.deleteDeCheckedNote(note)
                    if (noteViewModel.isRecyclerViewCheckable()) {
                        changeSelectBarToMainBottomBar()
                    }
                }
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
                noteViewModel.clearAllOrDeleteNote()
                noteViewModel.clearCheckedNotes()
                changeSelectBarToMainBottomBar()
            }
            R.id.menu_list_settings -> {
                navigator().navigateToSettingsScreen()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        if (!noteViewModel.isRecyclerViewCheckable()) {
            noteViewModel.clearCheckedNotes()
        }
        super.onDestroyView()
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            this.title = getString(R.string.notes_list_fragment_toolbar_title)
            this.setBackgroundDrawable(
                ColorDrawable(ContextCompat.getColor(requireContext(), R.color.black))
            )
        }

        binding.todosIcon.setOnClickListener {
            navigator().navigateToTodosScreen()
        }
        binding.addNoteItemFab.setOnClickListener {
            navigator().navigateToCreateNoteScreen()
        }
        binding.deleteSelectedIcon.setOnClickListener {
            noteViewModel.checkedNotes.forEach { noteViewModel.delete(it) }
            noteViewModel.clearCheckedNotes()
            changeSelectBarToMainBottomBar()
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

    fun changeMainBottomBarToSelectBar() {
        binding.mainBottomToolbar.visibility = GONE
        binding.selectNotesBottomToolbar.visibility = VISIBLE
    }

    fun changeSelectBarToMainBottomBar() {
        binding.selectNotesBottomToolbar.visibility = GONE
        binding.mainBottomToolbar.visibility = VISIBLE
    }
}