package com.example.queuecumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import com.example.queuecumber.utils.ApiUtil

class HistoryHomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_home_page)

        val backButtonH = findViewById<ImageButton>(R.id.back_button_history)
        backButtonH.setOnClickListener {
            val intent = Intent(this, Homepage::class.java)
            startActivity(intent)
        }

        ApiUtil.historyRequest(this) { response ->
            val history = response.getJSONArray("history_items")
            for (i in 0 until history.length()) {
                val view: LinearLayout =
                    LayoutInflater.from(this).inflate(R.layout.history_list_element, null) as LinearLayout
                (view.getChildAt(0) as Button).text = history.getJSONObject(i).getString("song_name")
                val historyList = findViewById<LinearLayout>(R.id.history_list_layout)
                historyList.addView(view)
            }
        }
    }
}