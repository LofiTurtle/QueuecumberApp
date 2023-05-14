package com.example.queuecumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.queuecumber.utils.ApiUtil
import com.example.queuecumber.utils.TimeFormatter

class ListeningSessionsPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listening_sessions_page)

        // TODO find a way to get the activity_id and name from previous screen
        val activityId = 1

        findViewById<TextView>(R.id.activity_title).text = "An Activity"

        ApiUtil.sessionsRequest(this, activityId) {response ->
            val listeningSessions = response.getJSONArray("sessions")
            for (i in 0 until listeningSessions.length()) {
                val view:LinearLayout =
                    LayoutInflater.from(this).inflate(R.layout.listening_session_list_element, null) as LinearLayout
                val startTimeMillis = listeningSessions.getJSONObject(i).getLong("start_time_millis")
                val endTimeMillis = listeningSessions.getJSONObject(i).getLong("end_time_millis")
                val text = TimeFormatter.formatDate(startTimeMillis) + "\n" +
                        TimeFormatter.formatTime(startTimeMillis) + " - " +
                        TimeFormatter.formatTime(endTimeMillis)
                (view.getChildAt(0) as Button).text = text
                val sessionsList = findViewById<LinearLayout>(R.id.listening_session_list_layout)
                Log.i("ListeningSessionsPage", text)
                sessionsList.addView(view)
                view.setOnClickListener {
                    // TODO add listener to view songs
                }
            }
        }
    }
}