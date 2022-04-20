package com.example.teachnotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.teachnotes.R
import com.example.teachnotes.databases.NoteDatabase
import com.example.teachnotes.databases.NoteRepository
import com.example.teachnotes.databinding.FragmentEditNoteBinding
import com.example.teachnotes.models.NoteViewModel
import com.example.teachnotes.models.NoteViewModelFactory


class EditNoteFragment : Fragment(R.layout.fragment_edit_note) {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var binding: FragmentEditNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_note,
            container, false
        )
        val dao = NoteDatabase.getInstance(requireActivity()).noteDAO
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        noteViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]
        binding.myViewModel = noteViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val text = arguments?.getString(ARGUMENT_TEXT) ?: "Unknown"
        val title = arguments?.getString(ARGUMENT_TITLE) ?: "Unknown"

        binding.noteTitle.setText(title)
        binding.noteText.setText(text)
        setHasOptionsMenu(true)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Edit Note"
    }

    companion object {
        private const val ARGUMENT_TITLE = "ARGUMENT_TITLE"
        private const val ARGUMENT_TEXT = "ARGUMENT_TEXT"

        fun newInstance(text: String, title: String): EditNoteFragment {
            val args = Bundle().apply {
                putString(ARGUMENT_TEXT, text)
                putString(ARGUMENT_TITLE, title)
            }

            val fragment = EditNoteFragment()
            fragment.arguments = args
            return fragment
        }
    }

}