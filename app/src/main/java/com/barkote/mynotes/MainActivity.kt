package com.barkote.mynotes

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.barkote.mynotes.const.EXTRA_NOTE_POSITION
import com.barkote.mynotes.const.POSITION_NOT_SET
import com.barkote.mynotes.data.DataManager
import com.barkote.mynotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var notePosition = POSITION_NOT_SET

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapterCourses =  ArrayAdapter(this,R.layout.drop_down_item,DataManager.courses.values.toList())

        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spin.setAdapter(adapterCourses)


        notePosition = intent.getIntExtra(EXTRA_NOTE_POSITION, POSITION_NOT_SET)

        if(notePosition != POSITION_NOT_SET){

            Log.d("TAG", "onCreate:  $notePosition ")
            displayNote(notePosition)
        }


    }

    private fun displayNote(notePosition: Int) {
        val note = DataManager.notes.get(notePosition)

        binding.title.setText(note.title)
        binding.content.setText(note.text)

        val list = DataManager.courses.values.toList()

        val i = list.indexOf(note.course)

        binding.spin.setText(note.course.toString())
    }
}