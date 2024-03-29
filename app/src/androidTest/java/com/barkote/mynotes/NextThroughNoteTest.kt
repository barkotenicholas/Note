package com.barkote.mynotes

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.barkote.mynotes.data.DataManager
import com.barkote.mynotes.data.NoteInfo
import org.hamcrest.CoreMatchers.*
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NextThroughNoteTest {

    @Rule
    @JvmField
    val noteListActivity = ActivityTestRule(NoteList::class.java);


    @Test
    fun nextThroughNotes() {

        onData(
            allOf(
                instanceOf(NoteInfo::class.java),
                equalTo(DataManager.notes[0])
            )
        ).perform(click())

        for (index in 0..DataManager.notes.lastIndex) {

            val note = DataManager.notes[index]

            onView(withId(R.id.spinner)).check(matches(withSpinnerText(note.course?.title)))
            onView(withId(R.id.title)).check(matches(withText(note.title)))
            onView(withId(R.id.content)).check(matches(withText(note.text)))


            if (index != DataManager.notes.lastIndex) {
                onView(allOf(withId(R.id.action_next), isEnabled())).perform(click())
            }

        }

        onView(withId(R.id.action_next)).check(matches(isEnabled()))
    }
}