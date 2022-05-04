package com.example.teachnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teachnotes.databinding.ActivityMainBinding
import com.example.teachnotes.screen.note_create.CreateNoteFragment
import com.example.teachnotes.screen.note_edit.EditNoteFragment
import com.example.teachnotes.screen.note_list.NotesListFragment
import com.example.teachnotes.screen.settings.SettingsFragment

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

    override fun onSupportNavigateUp(): Boolean {
        return if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            false
        } else {
            finish()
            true
        }
    }

    override fun goBack() {
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
            .replace(R.id.notes_container, CreateNoteFragment(), null)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToEditNoteScreen(noteId: Long) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.notes_container, EditNoteFragment.newInstance(noteId), null)
            .addToBackStack(null)
            .commit()
    }

}