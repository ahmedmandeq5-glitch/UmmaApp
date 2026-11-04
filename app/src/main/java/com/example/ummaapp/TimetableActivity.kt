package com.example.ummaapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.ummaapp.data.AppDatabase

class TimetableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)

        val list = findViewById<ListView>(R.id.lvTimetable)
        val userId = intent.getIntExtra("userId", -1)
        val dao = AppDatabase.getInstance(applicationContext).timetableDao()

        dao.getForStudent(userId).observe(this) { items ->
            val lines = if (items.isEmpty()) {
                listOf("No timetable found. Log in as a student to see one.")
            } else {
                items.map { "${it.day}  ${it.time}\n${it.code} - ${it.title}\n${it.venue}" }
            }
            list.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lines)
        }
    }
}