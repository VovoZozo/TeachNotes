package com.example.teachnotes.activities

import android.os.Bundle
import android.view.View
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

    fun showToolbarSearchView() {
        binding.searchView.visibility = View.VISIBLE
    }

    fun hideToolbarSearchView() {
        binding.searchView.visibility = View.GONE
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
        hideToolbarSearchView()
    }

    override fun navigateToCreateNoteScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.notes_container, CreateNoteFragment(), null)
            .addToBackStack(null)
            .commit()
        hideToolbarSearchView()
    }

    override fun navigateToNotesListScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.notes_container, NotesListFragment(), null)
            .commit()
        showToolbarSearchView()
    }

    override fun navigateToSettingsScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.notes_container, SettingsFragment(), null)
            .addToBackStack(null)
            .commit()
        hideToolbarSearchView()
    }

    override fun navigateToTodosScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.notes_container, TodosFragment(), null)
            .commit()
        hideToolbarSearchView()
    }

}