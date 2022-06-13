package com.barkote.mynotes

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.barkote.mynotes.const.EXTRA_NOTE_POSITION
import com.barkote.mynotes.const.POSITION_NOT_SET
import com.barkote.mynotes.data.CourseInfo
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
            displayNote()
        }


        binding.spin.setOnItemClickListener { adapterView, view, i, l ->
            val note = DataManager.notes[notePosition]
            val course = adapterView.getItemAtPosition(i)
            note.course = course as CourseInfo
        }


    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]

        binding.title.setText(note.title)
        binding.content.setText(note.text)

        val item =  DataManager.courses.values.toList().indexOf(note.course)

        binding.spin.setText(note.course.toString())
        binding.spin.setSelection(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_next -> {
                moveNext()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {

        ++notePosition
        displayNote()
        invalidateOptionsMenu()

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        if(notePosition >= DataManager.notes.lastIndex){
            val menuItem = menu?.findItem(R.id.action_next)
            if(menuItem != null){
                menuItem.icon =getDrawable(R.drawable.ic_block)
                menuItem.isEnabled = false
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        if(notePosition != POSITION_NOT_SET) {

            saveNote()
        }
    }

    private fun saveNote() {

        val note = DataManager.notes[notePosition]

        note.title = binding.title.text.toString()
        note.text  = binding.content.text.toString()



    }
}