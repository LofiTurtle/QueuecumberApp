package com.example.queuecumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.queuecumber.utils.ApiUtil

class CreateNewUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_user_activity)

        val backButtonNewUserActivity = findViewById<ImageButton>(R.id.back_button_new_user_activity)
        backButtonNewUserActivity.setOnClickListener {
            finish()
        }

        val createButton = findViewById<Button>(R.id.create_button)
        createButton.setOnClickListener {
            val textField = findViewById<EditText>(R.id.name_new_activity)
            val activityName = textField.text.toString()
            ApiUtil.createActivity(this, activityName)
        }
    }
}