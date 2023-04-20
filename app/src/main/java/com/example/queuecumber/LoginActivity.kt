package com.example.queuecumber

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var loginButton = findViewById<Button>(R.id.login)
        loginButton.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://accounts.spotify.com/en/login?continue=https%3A%2F%2Fwww.spotify.com%2Fus%2Faccount%2Ftwostepauth%2Fmanage%2F&max_age=0"))
            startActivity(i)
        }
    }
}