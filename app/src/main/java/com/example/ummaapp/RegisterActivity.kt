package com.example.ummaapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ummaapp.data.AppDatabase
import com.example.ummaapp.data.entity.User
import com.example.ummaapp.data.entity.Enrollment
import com.example.ummaapp.utils.Security
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etRegNumber = findViewById<EditText>(R.id.etRegNumber)
        val etDepartment = findViewById<EditText>(R.id.etDepartment)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvMessage = findViewById<TextView>(R.id.tvMessage)

        btnRegister.setOnClickListener {
            val fullName = etFullName.text.toString().trim()
            val regNumber = etRegNumber.text.toString().trim()
            val department = etDepartment.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                tvMessage.text = "Name, email and password are required"
                return@setOnClickListener
            }
            if (password.length < 6) {
                tvMessage.text = "Password must be at least 6 characters"
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val dao = AppDatabase.getInstance(applicationContext).userDao()

                if (dao.findByEmail(email) != null) {
                    tvMessage.text = "That email is already registered"
                } else {
                    dao.insert(
                        User(
                            fullName = fullName,
                            email = email,
                            password = Security.hash(password),
                            role = "STUDENT",
                            regNumber = regNumber,
                            department = department,
                            phone = phone
                        )
                    )
                    val saved = dao.findByEmail(email)
                    if (saved != null) {
                        val enrollDao = AppDatabase.getInstance(applicationContext).enrollmentDao()
                        for (courseId in 1..3) {
                            enrollDao.insert(Enrollment(studentId = saved.id, courseId = courseId, semester = "Semester 1"))
                        }
                    }
                    tvMessage.text = "Account created. Go back and log in."
                }
            }
        }
    }
}