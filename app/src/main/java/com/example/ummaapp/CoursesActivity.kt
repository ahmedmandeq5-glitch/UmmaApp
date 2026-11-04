package com.example.ummaapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.ummaapp.data.AppDatabase

class CoursesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courses)

        val list = findViewById<ListView>(R.id.lvCourses)
        val dao = AppDatabase.getInstance(applicationContext).courseDao()

        dao.getAll().observe(this) { courses ->
            val lines = courses.map { "${it.code} - ${it.title} (${it.credits} credits)" }
            list.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lines)
        }
    }
}