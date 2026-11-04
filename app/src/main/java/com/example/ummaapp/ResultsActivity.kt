package com.example.ummaapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.ummaapp.data.AppDatabase

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val list = findViewById<ListView>(R.id.lvResults)
        val userId = intent.getIntExtra("userId", -1)
        val dao = AppDatabase.getInstance(applicationContext).resultDao()

        dao.getResultsForStudent(userId).observe(this) { items ->
            val lines = if (items.isEmpty()) {
                listOf("No results found. Log in as a student to see results.")
            } else {
                items.map { "${it.code} - ${it.title}\nMarks: ${it.marks}   Grade: ${it.grade}" }
            }
            list.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lines)
        }
    }
}