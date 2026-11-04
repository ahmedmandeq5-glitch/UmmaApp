package com.example.ummaapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name = intent.getStringExtra("fullName") ?: "User"
        val role = intent.getStringExtra("role") ?: ""

        findViewById<TextView>(R.id.tvWelcome).text = "Welcome, $name"
        findViewById<TextView>(R.id.tvRole).text = "Signed in as $role"

        val soon = { label: String ->
            Toast.makeText(this, "$label screen coming next", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnCourses).setOnClickListener {
            startActivity(Intent(this, CoursesActivity::class.java))
        }
        findViewById<Button>(R.id.btnTimetable).setOnClickListener {
            val i = Intent(this, TimetableActivity::class.java)
            i.putExtra("userId", intent.getIntExtra("userId", -1))
            startActivity(i)
        }
        findViewById<Button>(R.id.btnResults).setOnClickListener {
            val i = Intent(this, ResultsActivity::class.java)
            i.putExtra("userId", intent.getIntExtra("userId", -1))
            startActivity(i)
        }
        findViewById<Button>(R.id.btnAnnouncements).setOnClickListener {
            startActivity(Intent(this, AnnouncementsActivity::class.java))
        }
        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}