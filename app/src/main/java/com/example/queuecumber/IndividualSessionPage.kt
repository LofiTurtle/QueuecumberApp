package com.example.queuecumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class IndividualSessionPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_session_page)

        val backButtonIS = findViewById<ImageButton>(R.id.back_button_individual_session)
        backButtonIS.setOnClickListener {
            finish()
        }
    }
}