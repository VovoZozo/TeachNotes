package com.example.teachnotes.screen.note_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.teachnotes.AppExecutors
import com.example.teachnotes.R
import com.example.teachnotes.databases.NoteDatabase
import com.example.teachnotes.databinding.FragmentEditNoteBinding
import com.example.teachnotes.navigator
import com.example.teachnotes.repository.NoteRepository

class EditNoteFragment : Fragment(R.layout.fragment_edit_note) {

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EditNoteViewModel

    private fun initViewModel(noteId: Long) {
        val dao = NoteDatabase.getInstance(requireContext()).noteDAO
        val repository = NoteRepository(dao, AppExecutors.ioExecutor)
        val factory = EditNoteViewModelFactory(noteId, repository)
        viewModel = ViewModelProvider(this, factory)[EditNoteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditNoteBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        val noteId = arguments?.getLong(ARGUMENT_NOTE_ID) ?: DEFAULT_NOTE_ID
        initViewModel(noteId)

        viewModel.currentNote.observe(viewLifecycleOwner) {
            binding.noteTitle.setText(it.noteTitle)
            binding.noteText.setText(it.noteText)
            if (it.isFavorite) {
                binding.noteFavorite.visibility = View.VISIBLE
            }
        }

        binding.isNoteFavorite.setOnClickListener {
            viewModel.onIsNoteFavoriteClick()
            if (viewModel.currentNote.value?.isFavorite == true) {
                binding.noteFavorite.visibility = View.VISIBLE
            } else {
                binding.noteFavorite.visibility = View.GONE
            }
        }

        binding.navigateUpButton.setOnClickListener {
            navigator().goBack()
        }

        binding.saveNoteFab.setOnClickListener {
            navigator().goBack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.currentNote.value?.noteTitle = binding.noteTitle.text.toString()
        viewModel.currentNote.value?.noteText = binding.noteText.text.toString()
        viewModel.updateNote()
        _binding = null
    }

    companion object {
        private const val DEFAULT_NOTE_ID = 0L
        private const val ARGUMENT_NOTE_ID = "ARGUMENT_NOTE_ID"
        fun newInstance(noteId: Long): EditNoteFragment {
            val args = Bundle().apply {
                putLong(ARGUMENT_NOTE_ID, noteId)
            }
            val fragment = EditNoteFragment()
            fragment.arguments = args
            return fragment
        }
    }
}