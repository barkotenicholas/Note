package com.barkote.mynotes.data

object DataManager {

    val courses = HashMap<String, CourseInfo>()
    val notes =ArrayList<NoteInfo>()

    init {
        initializeCourses()
        initializeNotes()
    }
    private fun initializeCourses(){

        var course = CourseInfo("android_intent","Android Programming with intent")
        courses.set(course.courseId,course)

        course = CourseInfo("android_async","Android async programming and services")
        courses.set(course.courseId,course)

        course = CourseInfo(title = "Java fundamentals : The java language", courseId = "java_lang")
        courses.set(course.courseId,course)

        course = CourseInfo("java_core","Java fundamentals : The core platform")
        courses.set(course.courseId,course)

    }

    private fun initializeNotes() {

        var note = NoteInfo(courses.get("android_intent"),"Introduction to intents","AN intent is used to start new activities")
        notes.add(note)

        note = NoteInfo(courses.get("android_async"),"Introduction to Async","Async is used for background services")
        notes.add(note)

        note = NoteInfo(courses.get("java_lang"),"Introduction to intents","Java as a programming language")
        notes.add(note)

        note = NoteInfo(courses.get("java_lang"),"Introduction to intents","How to ccreate a classs in java")
        notes.add(note)

        note = NoteInfo(courses.get("java_core"),"Introduction to intents","Uses of java")
        notes.add(note)



    }




}