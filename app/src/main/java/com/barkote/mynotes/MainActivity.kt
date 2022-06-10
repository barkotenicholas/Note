package com.barkote.mynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.barkote.mynotes.data.CourseInfo
import com.barkote.mynotes.data.DataManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val dm = DataManager()
        val adapterCourses =  ArrayAdapter<CourseInfo>(this,android.R.layout.simple_spinner_item,dm.courses.values.toList())

        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)



    }
}