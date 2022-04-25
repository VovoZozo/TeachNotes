package com.example.teachnotes.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.teachnotes.R
import com.example.teachnotes.databases.Note
import com.example.teachnotes.databases.NoteDatabase
import com.example.teachnotes.databases.NoteRepository
import com.example.teachnotes.databinding.FragmentNoteBinding
import com.example.teachnotes.models.NoteViewModel
import com.example.teachnotes.models.NoteViewModelFactory

class NoteFragment : Fragment(R.layout.fragment_note) {
    private lateinit var noteViewModel: NoteViewModel
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private var noteTitle: String = EMPTY_TEXT
    private var noteText: String = EMPTY_TEXT
    private var noteId: Int = DEFAULT_NOTE_ID
    private var isNoteFavorite = false
    private var isNoteNew = true
    private val isNoteEmpty
        get(): Boolean {
            return noteId == DEFAULT_NOTE_ID && noteText.isEmpty() && noteTitle.isEmpty()
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(
            inflater,
            container, false
        )
        val dao = NoteDatabase.getInstance(requireActivity()).noteDAO
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        noteViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        if (arguments != null) {
//            noteTitle = requireArguments().getString(ARGUMENT_TITLE).toString()
//            noteText = requireArguments().getString(ARGUMENT_TEXT).toString()
//            noteId = requireArguments().getInt(ARGUMENT_ID)
//            isNoteFavorite = requireArguments().getBoolean(ARGUMENT_IS_FAVORITE)
//            if (isNoteFavorite) {
//                binding.noteFavorite.visibility = View.VISIBLE
//            }
            isNoteFavorite = noteViewModel.currentNote.value?.isFavorite ?: false
            if (isNoteFavorite) {
                binding.noteFavorite.visibility = View.VISIBLE
            }
            binding.noteTitle.setText(noteViewModel.currentNote.value?.noteTitle ?: "")
            Log.d("rer", "${noteViewModel.currentNote.value}")
            binding.noteText.setText(noteViewModel.currentNote.value?.noteText ?: "")
            isNoteNew = false
        }

        binding.isNoteFavorite.setOnClickListener {

            isNoteFavorite = !isNoteFavorite
            if (isNoteFavorite) {
                binding.noteFavorite.visibility = View.VISIBLE
            } else {
                binding.noteFavorite.visibility = View.GONE
            }
        }

        binding.navigateUpButton.setOnClickListener {
            navigator().navigateUp()
        }

        binding.noteTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count >= TITLE_MAX_LENGTH) {
                    Toast.makeText(requireContext(), TITLE_MAX_LENGTH_MESSAGE, Toast.LENGTH_LONG)
                        .show()
                }
            }
            override fun afterTextChanged(s: Editable?) {
                noteTitle = s.toString()
            }
        })
        binding.noteText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                noteText = s.toString()
            }
        })
        binding.saveNoteFab.setOnClickListener {
            navigator().navigateToNotesListScreen()
        }
    }

    private fun saveNote() {
        if (noteId == DEFAULT_NOTE_ID) {
            noteViewModel.saveNote(noteTitle, noteText, isNoteFavorite)
        } else {
            noteViewModel.updateNote(noteId, noteTitle, noteText, isNoteFavorite)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (!isNoteEmpty) {
            saveNote()
        } else {
            Toast.makeText(requireContext(), DISCARD_NOTE_MESSAGE, Toast.LENGTH_SHORT).show()
        }
        _binding = null
    }

    companion object {
        private const val DEFAULT_NOTE_ID = -1
        private const val EMPTY_TEXT = ""
        private const val TITLE_MAX_LENGTH_MESSAGE = "You enter to match characters"
        private const val TITLE_MAX_LENGTH = 40
        private const val DISCARD_NOTE_MESSAGE = "Discard empty note"
        private const val ARGUMENT_ID = "ARGUMENT_ID"
        private const val ARGUMENT_TITLE = "ARGUMENT_TITLE"
        private const val ARGUMENT_TEXT = "ARGUMENT_TEXT"
        private const val ARGUMENT_IS_EDIT_NOTE = "ARGUMENT_IS_EDIT_NOTE"
        private const val ARGUMENT_IS_FAVORITE = "ARGUMENT_IS_FAVORITE"
        fun newInstance(note: Note): NoteFragment {
            val args = Bundle().apply {
//                putInt(ARGUMENT_ID, note.noteId)
//                putString(ARGUMENT_TITLE, note.noteTitle)
//                putString(ARGUMENT_TEXT, note.noteText)
//                putBoolean(ARGUMENT_IS_FAVORITE, note.isFavorite)
                putBoolean(ARGUMENT_IS_EDIT_NOTE, true)
            }
            val fragment = NoteFragment()
            fragment.arguments = args
            return fragment
        }
    }
}