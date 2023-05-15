package com.example.queuecumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class UnlabeledListeningSessions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unlabeled_listening_sessions)

        val backButtonUS = findViewById<ImageButton>(R.id.back_button_unlabeled_sessions)
        backButtonUS.setOnClickListener {
            finish()
        }
    }
}