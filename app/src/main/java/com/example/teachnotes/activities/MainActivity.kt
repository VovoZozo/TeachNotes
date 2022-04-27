package com.example.teachnotes.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.teachnotes.R
import com.example.teachnotes.databases.Note
import com.example.teachnotes.databases.NoteDatabase
import com.example.teachnotes.databases.NoteRepository
import com.example.teachnotes.databinding.ActivityMainBinding
import com.example.teachnotes.fragments.*
import com.example.teachnotes.models.NoteViewModel
import com.example.teachnotes.models.NoteViewModelFactory

class MainActivity : AppCompatActivity(), Navigator {
    private lateinit var noteModel: NoteViewModel
    private lateinit var binding: ActivityMainBinding
    private val canNavigateUp: Boolean
        get() = supportFragmentManager.backStackEntryCount > 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = NoteDatabase.getInstance(this).noteDAO
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        noteModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)


        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()

        if (savedInstanceState == null) {
            transaction.add(R.id.notes_container, NotesListFragment())
            transaction.commit()
        }
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportFragmentManager.addOnBackStackChangedListener {
            supportActionBar?.setDisplayHomeAsUpEnabled(canNavigateUp)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            false
        } else {
            finish()
            true
        }
    }


    override fun navigateToNotesListScreen() {
        onSupportNavigateUp()
    }

    override fun navigateToSettingsScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.notes_container, SettingsFragment(), null)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToCreateNoteScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.notes_container, NoteFragment(), null)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToEditNoteScreen(note: Note) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.notes_container, NoteFragment.newInstance(note), null)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToTodosScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.notes_container, TodosFragment(), null)
            .commit()
    }

    override fun navigateUp() {
        onSupportNavigateUp()
    }

}