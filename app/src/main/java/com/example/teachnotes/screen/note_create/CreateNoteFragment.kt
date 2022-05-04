package com.example.teachnotes.screen.note_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.teachnotes.AppExecutors
import com.example.teachnotes.R
import com.example.teachnotes.databases.NoteDatabase
import com.example.teachnotes.databinding.FragmentEditNoteBinding
import com.example.teachnotes.navigator
import com.example.teachnotes.repository.NoteRepository


class CreateNoteFragment : Fragment(R.layout.fragment_create_note) {

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CreateNoteViewModel

    private fun initViewModel() {
        val dao = NoteDatabase.getInstance(requireContext()).noteDAO
        val repository = NoteRepository(dao, AppExecutors.ioExecutor)
        val factory = CreateNoteViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[CreateNoteViewModel::class.java]
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
        initViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                saveNote()
                navigator().goBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.isNoteFavorite.setOnClickListener {
            viewModel.onIsNoteFavoriteClick()
            if (viewModel.isNoteFavorite) {
                binding.noteFavorite.visibility = View.VISIBLE
            } else {
                binding.noteFavorite.visibility = View.GONE
            }
        }

        binding.saveNoteFab.setOnClickListener {
            saveNote()
            navigator().goBack()
        }
        binding.navigateUpButton.setOnClickListener {
            saveNote()
            navigator().goBack()
        }
    }

    private fun saveNote() {
        binding.apply {
            if (noteText.text.toString() == EMPTY_TEXT && noteTitle.text.toString() == EMPTY_TEXT) {
                Toast.makeText(requireContext(), DISCARD_EMPTY_NOTE_MESSAGE, Toast.LENGTH_SHORT)
                    .show()
                return
            } else {
                viewModel.saveNote(
                    noteTitle.text.toString(),
                    noteText.text.toString()
                )
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val EMPTY_TEXT = ""
        private const val DISCARD_EMPTY_NOTE_MESSAGE = "Discard empty note"
    }

}