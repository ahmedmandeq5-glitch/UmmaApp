package com.example.ummaapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ummaapp.data.AppDatabase
import com.example.ummaapp.utils.Security
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val tvMessage = findViewById<TextView>(R.id.tvMessage)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnGoRegister = findViewById<Button>(R.id.btnGoRegister)
        btnGoRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                tvMessage.text = "Enter your email and password"
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val dao = AppDatabase.getInstance(applicationContext).userDao()
                val user = dao.login(email, Security.hash(password))

                if (user == null) {
                    tvMessage.text = "Wrong email or password"
                } else {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("userId", user.id)
                    intent.putExtra("fullName", user.fullName)
                    intent.putExtra("role", user.role)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}