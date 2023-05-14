package com.example.queuecumber

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.queuecumber.utils.ApiUtil
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class HistoryHomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_home_page)

        val backButtonH = findViewById<ImageButton>(R.id.back_button_history)
        backButtonH.setOnClickListener {
            finish()
        }

        ApiUtil.historyRequest(this) { response ->
            val history = response.getJSONArray("history_items")
            for (i in 0 until history.length()) {
                val view: LinearLayout =
                    LayoutInflater.from(this).inflate(R.layout.history_list_element, null) as LinearLayout
                (view.getChildAt(1) as TextView).text = history.getJSONObject(i).getString("song_name")
                (view.getChildAt(2) as TextView).text = history.getJSONObject(i).getString("artist_name")
                val playedAtMillis = history.getJSONObject(i).getLong("played_at_millis")
                val playedAtDate = Date(playedAtMillis)
                val sdf = SimpleDateFormat("MMM d, yyyy - h:mm a", Locale.getDefault())
                (view.getChildAt(3) as TextView).text = sdf.format(playedAtDate)
                val historyList = findViewById<LinearLayout>(R.id.history_list_layout)
                historyList.addView(view)
            }
        }
    }
}