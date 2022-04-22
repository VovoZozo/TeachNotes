package com.example.teachnotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.teachnotes.R
import com.example.teachnotes.databases.NoteDatabase
import com.example.teachnotes.databases.NoteRepository
import com.example.teachnotes.databinding.FragmentCreateNoteBinding
import com.example.teachnotes.models.NoteViewModel
import com.example.teachnotes.models.NoteViewModelFactory

class CreateNoteFragment : Fragment(R.layout.fragment_edit_note) {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var binding: FragmentCreateNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_note,
            container, false
        )
        val dao = NoteDatabase.getInstance(requireActivity()).noteDAO
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        noteViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]
        binding.myViewModel = noteViewModel
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        return binding.root
    }

}