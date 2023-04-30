package com.example.queuecumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import com.example.queuecumber.utils.ApiUtil

class ActivityHomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage_activities_section)

        val backButton = findViewById<ImageButton>(R.id.back_button_activities)
        backButton.setOnClickListener {
            val intent = Intent(this, Homepage::class.java)
            startActivity(intent)
        }

        ApiUtil.activitiesRequest(this) { response ->
            Log.i("ActivityHomePage", "Made activitiesRequest() call successfully")
            val activities = response.getJSONArray("activities")
            for (i in 0 until activities.length()) {
                Log.i("Activity list", i.toString() + " : " + activities.getString(i))
            }
        }
    }
}