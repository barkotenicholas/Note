package com.barkote.mynotes

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.barkote.mynotes.data.DataManager
import com.barkote.mynotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapterCourses =  ArrayAdapter(this,R.layout.drop_down_item,DataManager.courses.values.toList())

        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spin.setAdapter(adapterCourses)






    }
}