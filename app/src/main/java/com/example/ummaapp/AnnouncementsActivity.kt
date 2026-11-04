package com.example.ummaapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.ummaapp.data.AppDatabase

class AnnouncementsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcements)

        val list = findViewById<ListView>(R.id.lvAnnouncements)
        val dao = AppDatabase.getInstance(applicationContext).announcementDao()

        dao.getAll().observe(this) { items ->
            val lines = items.map { "${it.title}\n${it.message}\n${it.date}" }
            list.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lines)
        }
    }
}