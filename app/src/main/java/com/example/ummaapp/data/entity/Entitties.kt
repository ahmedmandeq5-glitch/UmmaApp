package com.example.ummaapp.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users", indices = [Index("email", unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val email: String,
    val password: String,
    val role: String,          // STUDENT, LECTURER or ADMIN
    val regNumber: String = "",
    val department: String = "",
    val phone: String = ""
)

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val code: String,
    val title: String,
    val description: String,
    val credits: Int,
    val lecturerId: Int
)

@Entity(tableName = "enrollments")
data class Enrollment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val studentId: Int,
    val courseId: Int,
    val semester: String
)

@Entity(tableName = "results")
data class Result(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val studentId: Int,
    val courseId: Int,
    val marks: Int,
    val grade: String
)

@Entity(tableName = "announcements")
data class Announcement(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val message: String,
    val date: String
)

@Entity(tableName = "timetable")
data class TimetableEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val courseId: Int,
    val day: String,
    val time: String,
    val venue: String
)