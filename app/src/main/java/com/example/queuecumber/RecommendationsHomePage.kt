package com.example.queuecumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class RecommendationsHomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendations_home_page)

        val backButtonR = findViewById<ImageButton>(R.id.back_button_recommendations)
        backButtonR.setOnClickListener {
            val intent = Intent(this, Homepage::class.java)
            startActivity(intent)
        }
    }
}