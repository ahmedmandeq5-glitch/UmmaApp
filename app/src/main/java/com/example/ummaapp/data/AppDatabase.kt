package com.example.ummaapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ummaapp.data.dao.*
import com.example.ummaapp.data.entity.*
import com.example.ummaapp.utils.Security
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [User::class, Course::class, Enrollment::class,
        Result::class, Announcement::class, TimetableEntry::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun courseDao(): CourseDao
    abstract fun enrollmentDao(): EnrollmentDao
    abstract fun resultDao(): ResultDao
    abstract fun announcementDao(): AnnouncementDao
    abstract fun timetableDao(): TimetableDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        // Only one database object exists for the whole app
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "umma_database"
                ).addCallback(object : Callback() {
                    // Runs once, when the database is first created
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            INSTANCE?.let { populate(it) }
                        }
                    }
                }).build().also { INSTANCE = it }
            }
        }

        // Sample data (fake records only, as the brief requires)
        private suspend fun populate(db: AppDatabase) {
            val users = db.userDao()
            // ids are given in this order: 1 admin, 2-3 lecturers, 4-6 students
            users.insert(User(fullName = "System Admin", email = "admin@umma.ac.ke",
                password = Security.hash("admin123"), role = "ADMIN", department = "Administration"))
            users.insert(User(fullName = "Dr. Amina Yusuf", email = "lecturer1@umma.ac.ke",
                password = Security.hash("lec123"), role = "LECTURER", department = "Computer Science"))
            users.insert(User(fullName = "Mr. Hassan Ali", email = "lecturer2@umma.ac.ke",
                password = Security.hash("lec123"), role = "LECTURER", department = "Computer Science"))
            users.insert(User(fullName = "Mandeeq Student", email = "student1@umma.ac.ke",
                password = Security.hash("stud123"), role = "STUDENT",
                regNumber = "CS/001/2022", department = "Computer Science", phone = "0700000001"))
            users.insert(User(fullName = "Faith Wanjiru", email = "student2@umma.ac.ke",
                password = Security.hash("stud123"), role = "STUDENT",
                regNumber = "CS/002/2022", department = "Computer Science", phone = "0700000002"))
            users.insert(User(fullName = "Ibrahim Omar", email = "student3@umma.ac.ke",
                password = Security.hash("stud123"), role = "STUDENT",
                regNumber = "CS/003/2022", department = "Computer Science", phone = "0700000003"))

            val courses = db.courseDao()
            courses.insert(Course(code = "CSE 2414", title = "Mobile Application Development",
                description = "Building Android apps with Kotlin.", credits = 3, lecturerId = 2))
            courses.insert(Course(code = "CSE 2201", title = "Database Systems",
                description = "Relational databases and SQL.", credits = 3, lecturerId = 2))
            courses.insert(Course(code = "CSE 2305", title = "Data Communications",
                description = "Networks, signals and error detection.", credits = 3, lecturerId = 3))
            courses.insert(Course(code = "BIT 2222", title = "Software Engineering",
                description = "Planning and building quality software.", credits = 3, lecturerId = 3))

            val enroll = db.enrollmentDao()
            for (student in 4..6) {
                for (course in 1..3) {
                    enroll.insert(Enrollment(studentId = student, courseId = course, semester = "Sem 1 2026"))
                }
            }

            val results = db.resultDao()
            results.insert(Result(studentId = 4, courseId = 1, marks = 78, grade = "B+"))
            results.insert(Result(studentId = 4, courseId = 2, marks = 85, grade = "A"))
            results.insert(Result(studentId = 4, courseId = 3, marks = 66, grade = "B"))
            results.insert(Result(studentId = 5, courseId = 1, marks = 72, grade = "B"))

            val news = db.announcementDao()
            news.insert(Announcement(title = "Welcome to Semester 1",
                message = "Classes begin on Monday. Check your timetable.", date = "2026-09-01"))
            news.insert(Announcement(title = "CAT II Reminder",
                message = "CAT II submissions are due Wednesday 4 November 2026.", date = "2026-10-30"))

            val timetable = db.timetableDao()
            timetable.insert(TimetableEntry(courseId = 1, day = "Monday", time = "8:00 - 10:00", venue = "Lab 1"))
            timetable.insert(TimetableEntry(courseId = 2, day = "Tuesday", time = "10:00 - 12:00", venue = "Room B4"))
            timetable.insert(TimetableEntry(courseId = 3, day = "Thursday", time = "2:00 - 4:00", venue = "Room A2"))
        }
    }
}
