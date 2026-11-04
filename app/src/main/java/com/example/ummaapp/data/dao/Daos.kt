package com.example.ummaapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ummaapp.data.entity.*

// Small classes that hold the result of a JOIN query
data class ResultWithCourse(
    val code: String, val title: String, val credits: Int,
    val marks: Int, val grade: String
)

data class TimetableItem(
    val code: String, val title: String,
    val day: String, val time: String, val venue: String
)

@Dao
interface UserDao {
    @Insert suspend fun insert(user: User): Long
    @Update suspend fun update(user: User)
    @Delete suspend fun delete(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): User?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun findByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): User?

    @Query("SELECT * FROM users ORDER BY fullName")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE role = :role ORDER BY fullName")
    fun getByRole(role: String): LiveData<List<User>>
}

@Dao
interface CourseDao {
    @Insert suspend fun insert(course: Course): Long
    @Update suspend fun update(course: Course)
    @Delete suspend fun delete(course: Course)

    @Query("SELECT * FROM courses ORDER BY code")
    fun getAll(): LiveData<List<Course>>

    @Query("SELECT * FROM courses WHERE lecturerId = :lecturerId ORDER BY code")
    fun getByLecturer(lecturerId: Int): LiveData<List<Course>>
}

@Dao
interface EnrollmentDao {
    @Insert suspend fun insert(enrollment: Enrollment)

    @Query("SELECT COUNT(*) FROM enrollments WHERE studentId = :studentId AND courseId = :courseId")
    suspend fun countEnrollment(studentId: Int, courseId: Int): Int

    @Query("SELECT c.* FROM courses c INNER JOIN enrollments e ON c.id = e.courseId WHERE e.studentId = :studentId")
    fun getCoursesForStudent(studentId: Int): LiveData<List<Course>>

    @Query("SELECT u.* FROM users u INNER JOIN enrollments e ON u.id = e.studentId WHERE e.courseId = :courseId ORDER BY u.fullName")
    fun getStudentsForCourse(courseId: Int): LiveData<List<User>>
}

@Dao
interface ResultDao {
    @Insert suspend fun insert(result: Result)
    @Update suspend fun update(result: Result)

    @Query("SELECT * FROM results WHERE studentId = :studentId AND courseId = :courseId LIMIT 1")
    suspend fun find(studentId: Int, courseId: Int): Result?

    @Query("SELECT c.code AS code, c.title AS title, c.credits AS credits, r.marks AS marks, r.grade AS grade FROM results r INNER JOIN courses c ON c.id = r.courseId WHERE r.studentId = :studentId")
    fun getResultsForStudent(studentId: Int): LiveData<List<ResultWithCourse>>
}

@Dao
interface AnnouncementDao {
    @Insert suspend fun insert(announcement: Announcement)
    @Delete suspend fun delete(announcement: Announcement)

    @Query("SELECT * FROM announcements ORDER BY id DESC")
    fun getAll(): LiveData<List<Announcement>>
}

@Dao
interface TimetableDao {
    @Insert suspend fun insert(entry: TimetableEntry)

    @Query("SELECT c.code AS code, c.title AS title, t.day AS day, t.time AS time, t.venue AS venue FROM timetable t INNER JOIN courses c ON c.id = t.courseId INNER JOIN enrollments e ON e.courseId = c.id WHERE e.studentId = :studentId")
    fun getForStudent(studentId: Int): LiveData<List<TimetableItem>>
}