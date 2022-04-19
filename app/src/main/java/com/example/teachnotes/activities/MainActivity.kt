package com.example.teachnotes.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teachnotes.R
import com.example.teachnotes.databinding.ActivityMainBinding
import com.example.teachnotes.fragments.EditNoteFragment
import com.example.teachnotes.fragments.Navigator
import com.example.teachnotes.fragments.NotesListFragment
import com.example.teachnotes.fragments.SettingsFragment

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding
    private val canNavigateUp: Boolean
        get() = supportFragmentManager.backStackEntryCount > 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //setSupportActionBar(findViewById(R.id.main_toolbar))

        var manager = supportFragmentManager
        var transaction = manager.beginTransaction()
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

    override fun navigateToEditNoteScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.notes_container, EditNoteFragment(), null)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToNotesListScreenFromBackStack() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.notes_container, NotesListFragment(), null)
            .commit()
    }

    override fun navigateToSettingsScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.notes_container, SettingsFragment(), null)
            .addToBackStack(null)
            .commit()
    }

}