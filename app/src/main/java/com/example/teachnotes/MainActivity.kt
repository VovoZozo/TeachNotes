package com.example.teachnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var manager = supportFragmentManager
        var transaction = manager.beginTransaction()
        if (savedInstanceState == null) {
            transaction.add(R.id.top_bar_container, MainTopBarFragment())
            transaction.add(R.id.notes_container, NotesListFragment())
            transaction.add(R.id.notes_container, MainBottomBarFragment())
            transaction.commit()
        }
    }
}