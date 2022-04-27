package com.example.teachnotes.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.teachnotes.R
import com.example.teachnotes.databases.Note
import com.example.teachnotes.databinding.FragmentNoteBinding
import com.example.teachnotes.models.NoteViewModel

class NoteFragment : Fragment(R.layout.fragment_note) {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel: NoteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        if (arguments != null) {
            arguments?.getParcelable<Note>(ARGUMENT_EDITED_NOTE)?.let {
                noteViewModel.initEditedNote(it)
            }
            binding.apply {
                noteTitle.setText(noteViewModel.editedNote.noteTitle)
                noteText.setText(noteViewModel.editedNote.noteText)
            }
            checkFavoriteState()
        } else {
            noteViewModel.initEditedNote(DEFAULT_NOTE)
        }

        binding.isNoteFavorite.setOnClickListener {
            noteViewModel.changeFavoriteState()
            checkFavoriteState()
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
                noteViewModel.editedNote.noteTitle = s.toString()
            }
        })
        binding.noteText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count >= TITLE_MAX_LENGTH) {
                    Toast.makeText(requireContext(), TITLE_MAX_LENGTH_MESSAGE, Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                noteViewModel.editedNote.noteText = s.toString()
            }
        })
        binding.saveNoteFab.setOnClickListener {
            navigator().navigateToNotesListScreen()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        noteViewModel.saveNote()
        _binding = null
    }

    private fun checkFavoriteState() {
        if (noteViewModel.editedNote.isFavorite) {
            binding.noteFavorite.visibility = View.VISIBLE
        } else {
            binding.noteFavorite.visibility = View.GONE
        }
    }

    companion object {
        private const val DEFAULT_NOTE_ID = 0
        private const val EMPTY_TEXT = ""
        private val DEFAULT_NOTE = Note(DEFAULT_NOTE_ID, EMPTY_TEXT, EMPTY_TEXT, false)
        private const val TITLE_MAX_LENGTH_MESSAGE = "You enter to match characters"
        private const val TITLE_MAX_LENGTH = 40
        private const val DISCARD_NOTE_MESSAGE = "Discard empty note"
        private const val ARGUMENT_EDITED_NOTE = "ARGUMENT_EDITED_NOTE"
        fun newInstance(note: Note?): NoteFragment {
            val args = Bundle().apply {
                putParcelable(ARGUMENT_EDITED_NOTE, note)
            }
            val fragment = NoteFragment()
            fragment.arguments = args
            return fragment
        }
    }
}