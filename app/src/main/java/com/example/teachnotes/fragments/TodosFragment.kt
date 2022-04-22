package com.example.teachnotes.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teachnotes.R
import com.example.teachnotes.adapters.TodosRecyclerAdapter
import com.example.teachnotes.databases.TodosRepository
import com.example.teachnotes.databinding.FragmentTodosBinding

class TodosFragment : Fragment(R.layout.fragment_todos) {

    private var _binding: FragmentTodosBinding? = null
    private val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return FragmentTodosBinding.inflate(inflater, container, false).also {
            this._binding = it
        }.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        binding.todosRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        var todos = TodosRepository.todos
        binding.todosRecyclerView.adapter = TodosRecyclerAdapter(todos)

        binding.notesIcon.setOnClickListener {
            navigator().navigateToNotesListScreen()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_menu_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_list_delete_all -> {
                Toast.makeText(
                    requireContext(),
                    "Delete all ToDO now doing nothing",
                    Toast.LENGTH_LONG
                ).show()
            }
            R.id.menu_list_settings -> {
                navigator().navigateToSettingsScreen()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}