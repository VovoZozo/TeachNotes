package com.example.teachnotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            parentFragmentManager.beginTransaction()
                .replace(R.id.notes_container, NotesListFragment()).commit()
        }
    }
}