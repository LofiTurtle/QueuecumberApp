package com.example.queuecumber

import android.content.Intent
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
        var activityInitialName: String = ""
        var activityName: String = ""
        var activityId: Int = -1
        if (extras != null) {
            activityInitialName = extras.getString("activity_name").toString()
            activityName = activityInitialName.uppercase()
            activityId = extras.getInt("activity_id")
        }

        val deleteButton = findViewById<Button>(R.id.delete_activity_button)
        deleteButton.setOnClickListener {
            ApiUtil.deleteActivity(this, activityId)
        }

        findViewById<TextView>(R.id.activity_title).text = activityName

        ApiUtil.sessionsRequest(this, activityId) {response ->
            val listeningSessions = response.getJSONArray("sessions")
            for (i in 0 until listeningSessions.length()) {
                val view:LinearLayout =
                    LayoutInflater.from(this).inflate(R.layout.listening_session_list_element, null) as LinearLayout
                val sessionId = listeningSessions.getJSONObject(i).getInt("id")
                val startTimeMillis = listeningSessions.getJSONObject(i).getLong("start_time_millis")
                val endTimeMillis = listeningSessions.getJSONObject(i).getLong("end_time_millis")
                val text = TimeFormatter.formatDate(startTimeMillis) + "\n" +
                        TimeFormatter.formatTime(startTimeMillis) + " - " +
                        TimeFormatter.formatTime(endTimeMillis)
                (view.getChildAt(0) as Button).text = text
                val sessionsList = findViewById<LinearLayout>(R.id.listening_session_list_layout)
                sessionsList.addView(view)
                (view.getChildAt(0) as Button).setOnClickListener {
                    val intent = Intent(this, IndividualSessionPage::class.java)
                    intent.putExtra("session_id", sessionId)
                    intent.putExtra("parent_activity", activityName)
                    intent.putExtra("start_time_millis", startTimeMillis)
                    startActivity(intent)
                }
            }
        }
    }
}