package com.example.queuecumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.example.queuecumber.utils.ApiUtil
import com.example.queuecumber.utils.TimeFormatter

class IndividualSessionPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_session_page)

        val backButtonIS = findViewById<ImageButton>(R.id.back_button_individual_session)
        backButtonIS.setOnClickListener {
            finish()
        }

        val extras = intent.extras
        var sessionId: Int = -1
        var activityName: String = ""
        var startTimeMillis: Long = -1
        if (extras != null) {
            sessionId = extras.getInt("session_id")
            activityName = extras.getString("parent_activity").toString()
            startTimeMillis = extras.getLong("start_time_millis")
        }

        val title = findViewById<TextView>(R.id.activity_title)
        val sessionTime = TimeFormatter.formatDateTime(startTimeMillis)
        title.text = "$activityName Session\n$sessionTime"

        val songsList = findViewById<LinearLayout>(R.id.individual_session_list_layout)

        ApiUtil.sessionSongsRequest(this, sessionId) {request ->
            val songs = request.getJSONArray("songs")
            for (i in 0 until songs.length()) {
                val view: LinearLayout =
                    LayoutInflater.from(this).inflate(R.layout.history_list_element, null) as LinearLayout
                (view.getChildAt(1) as TextView).text = songs.getJSONObject(i).getString("song_name")
                (view.getChildAt(2) as TextView).text = songs.getJSONObject(i).getString("artist_name")
                songsList.addView(view)
            }
        }
    }
}