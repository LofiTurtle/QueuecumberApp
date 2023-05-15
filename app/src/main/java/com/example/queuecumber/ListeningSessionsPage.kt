package com.example.queuecumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.example.queuecumber.utils.ApiUtil
import com.example.queuecumber.utils.TimeFormatter

class ListeningSessionsPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listening_sessions_page)

        val backButtonLS = findViewById<ImageButton>(R.id.back_button_listening_sessions)
        backButtonLS.setOnClickListener {
            finish()
        }

        val extras = intent.extras
        var activityName: String = ""
        var activityId: Int = -1
        if (extras != null) {
            activityName = extras.getString("activity_name").toString()
            activityId = extras.getInt("activity_id")
        }

        findViewById<TextView>(R.id.activity_title).text = activityName

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