package com.example.queuecumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import com.example.queuecumber.utils.ApiUtil
import com.example.queuecumber.utils.TimeFormatter

class UnlabeledListeningSessions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unlabeled_listening_sessions)

        val backButtonUS = findViewById<ImageButton>(R.id.back_button_unlabeled_sessions)
        backButtonUS.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        val sessionsList = findViewById<LinearLayout>(R.id.unlabeled_session_list_layout)
        sessionsList.removeAllViews()

        ApiUtil.unlabeledSessionsRequest(this) { response ->
            val listeningSessions = response.getJSONArray("sessions")
            for (i in 0 until listeningSessions.length()) {
                val view: LinearLayout =
                    LayoutInflater.from(this).inflate(R.layout.unlabeled_session_list_element, null) as LinearLayout
                val startTimeMillis = listeningSessions.getJSONObject(i).getLong("start_time_millis")
                val endTimeMillis = listeningSessions.getJSONObject(i).getLong("end_time_millis")
                val sessionId = listeningSessions.getJSONObject(i).getInt("id")
                Log.i("session_id", sessionId.toString())
                val text = TimeFormatter.formatDate(startTimeMillis) + "\n" +
                        TimeFormatter.formatTime(startTimeMillis) + " - " +
                        TimeFormatter.formatTime(endTimeMillis)
                (view.getChildAt(0) as Button).text = text
                sessionsList.addView(view)
                (view.getChildAt(0) as Button).setOnClickListener {
                    val intent = Intent(this, ListOfListeningActivities::class.java)
                    intent.putExtra("id", sessionId)
                    Log.i("session intent", sessionId.toString())
                    startActivity(intent)
                }
            }
        }
    }
}