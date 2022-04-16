package com.example.teachnotes.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teachnotes.R
import com.example.teachnotes.databinding.ActivityMainBinding
import com.example.teachnotes.fragments.NotesListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.main_toolbar))

        var manager = supportFragmentManager
        var transaction = manager.beginTransaction()
        if (savedInstanceState == null) {
            transaction.add(R.id.notes_container, NotesListFragment())
            transaction.commit()
        }

    }

}