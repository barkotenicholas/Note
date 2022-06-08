package com.barkote.mynotes.data

class DataManager {

    val courses = HashMap<String, CourseInfo>()
    val notes =ArrayList<NoteInfo>()

    init {
        initializeCourses()
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



}