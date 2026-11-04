package com.example.ummaapp.utils

import java.security.MessageDigest

object Security {
    // Turns a password into a scrambled 64-character code (SHA-256).
    // The same password always gives the same code, but the code
    // cannot be turned back into the password.
    fun hash(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}