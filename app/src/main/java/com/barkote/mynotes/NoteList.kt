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


        binding.notelist.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,DataManager.notes)

        binding.notelist.setOnItemClickListener { _, _, i, _ ->
            val intent   = Intent(this,MainActivity::class.java)
            intent.putExtra(NOTE_POSITION,i)

            startActivity(intent)
        }

    }


    override fun onResume() {
        super.onResume()
        (binding.notelist.adapter as ArrayAdapter<*>).notifyDataSetChanged()
    }
}