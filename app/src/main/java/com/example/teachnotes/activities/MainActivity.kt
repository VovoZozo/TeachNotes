package com.example.teachnotes.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.teachnotes.R
import com.example.teachnotes.databases.Note
import com.example.teachnotes.databinding.ActivityMainBinding
import com.example.teachnotes.fragments.*
import com.example.teachnotes.models.NoteViewModel

class MainActivity : AppCompatActivity(), Navigator {
    val noteModel: NoteViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val canNavigateUp: Boolean
        get() = supportFragmentManager.backStackEntryCount > 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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