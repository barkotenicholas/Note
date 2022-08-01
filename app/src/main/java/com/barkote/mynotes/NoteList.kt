package com.barkote.mynotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.barkote.mynotes.const.NOTE_POSITION
import com.barkote.mynotes.data.DataManager
import com.barkote.mynotes.databinding.ActivityNoteListBinding

class NoteList : AppCompatActivity() {

    private lateinit var binding: ActivityNoteListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            val intent   = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }




    }


    override fun onResume() {
        super.onResume()
    }
}