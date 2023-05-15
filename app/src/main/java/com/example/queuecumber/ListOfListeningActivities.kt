package com.example.queuecumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class ListOfListeningActivities : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_listening_activities)

        val backButtonLA = findViewById<ImageButton>(R.id.back_button_activity_selection)
        backButtonLA.setOnClickListener {
            finish()
        }
    }
}