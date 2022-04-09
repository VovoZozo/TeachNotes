package com.example.teachnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.top_bar_container, MainTopBarFragment())
                .commit()
            supportFragmentManager.beginTransaction()
                .add(R.id.bottom_bar_container, MainBottomBarFragment())
                .commit()
        }

    }
}