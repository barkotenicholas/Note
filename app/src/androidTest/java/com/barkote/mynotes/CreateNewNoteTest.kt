package com.barkote.mynotes

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.barkote.mynotes.data.CourseInfo
import com.barkote.mynotes.data.DataManager
import junit.framework.TestCase.assertEquals
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
internal class CreateNewNoteTest{

    @Rule @JvmField
    val noteListActivity = ActivityTestRule(NoteList::class.java);


    @Test
    fun createNewNote(){

        val noteTitle = "Test Note Title"
        val noteText = "Test Note text"
        val course = DataManager.courses["java_lang"]

        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.spinner)).perform(click())
        onData(allOf(instanceOf(CourseInfo::class.java), equalTo(course))).perform(click())

        onView(withId(R.id.title)).perform(typeText(noteTitle))
        onView(withId(R.id.content)).perform(typeText(noteText), closeSoftKeyboard())

        onView(withId(R.id.save)).perform(click())



        Espresso.pressBackUnconditionally()

        val note = DataManager.notes.last()
        assertEquals(course,note.course)
        assertEquals(noteTitle,note.title)
        assertEquals(noteText,note.text)

    }
}