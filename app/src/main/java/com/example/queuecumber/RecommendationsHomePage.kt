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

class RecommendationsHomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendations_home_page)

        val backButtonR = findViewById<ImageButton>(R.id.back_button_recommendations)
        backButtonR.setOnClickListener {
            finish()
        }

        ApiUtil.activityPlaylistsRequest(this) { response ->
            Log.i("RecommendationsHomePage", "Made activityPlaylistsRequest() call successfully")
            val recommendations = response.getJSONArray("playlists")
            for (i in 0 until recommendations.length()) {
                Log.i("Recommendations list", i.toString() + " : " + recommendations.getString(i))
                val view: LinearLayout =
                    LayoutInflater.from(this).inflate(R.layout.recommendations_list_element, null) as LinearLayout
                // endpoint does not return the playlist name, only the activity name. So just say "{activity_name} Music"
                (view.getChildAt(1) as Button).text = recommendations.getJSONObject(i).getString("activity_name") + " Music"
                val recommendationsList = findViewById<LinearLayout>(R.id.recommendations_list_layout)
                recommendationsList.addView(view)
            }
        }
    }
}