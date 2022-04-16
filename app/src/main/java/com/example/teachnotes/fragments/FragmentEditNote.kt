package com.example.teachnotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.teachnotes.R
import com.example.teachnotes.databinding.FragmentEditNoteBinding


class FragmentEditNote : Fragment(R.layout.fragment_edit_note) {

    private var _binding: FragmentEditNoteBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentEditNoteBinding.inflate(inflater, container, false).also {
            this._binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createNoteLayout.setOnClickListener {
            binding.showNoteView.visibility = View.GONE
            binding.editNoteView.visibility = View.VISIBLE
            binding.saveNewNoteFab.visibility = View.VISIBLE
        }
        binding.saveNewNoteFab.setOnClickListener {
            binding.showNoteView.text = binding.editNoteView.text
            binding.editNoteView.visibility = View.GONE
            binding.saveNewNoteFab.visibility = View.GONE
            binding.showNoteView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }

}