package com.example.teachnotes.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teachnotes.R
import com.example.teachnotes.databases.Note
import com.example.teachnotes.databinding.ActivityMainBinding
import com.example.teachnotes.fragments.*

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding
    private val canNavigateUp: Boolean
        get() = supportFragmentManager.backStackEntryCount > 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()

        if (savedInstanceState == null) {
            transaction.add(R.id.notes_container, NotesListFragment())
            transaction.commit()
        }

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

    override fun navigateToEditNoteScreen(note: Note) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.notes_container, EditNoteFragment.newInstance(note), null
            )
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToCreateNoteScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.notes_container, CreateNoteFragment(), null)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToNotesListScreenFromBackStack() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.notes_container, NotesListFragment(), null)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToSettingsScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.notes_container, SettingsFragment(), null)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToTodosScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.notes_container, TodosFragment(), null)
            .addToBackStack(null)
            .commit()
    }

}