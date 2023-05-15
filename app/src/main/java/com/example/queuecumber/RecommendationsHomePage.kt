package com.example.queuecumber

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
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
                val view: LinearLayout =
                    LayoutInflater.from(this).inflate(R.layout.recommendations_list_element, null) as LinearLayout

                // endpoint does not return the playlist name, only the activity name.
                // So just say "{activity_name} Music"
                val text = recommendations.getJSONObject(i).getString("activity_name") + " Music"
                ((view.getChildAt(1) as LinearLayout).getChildAt(0) as TextView).text = text

                // set the button to either create or open the playlist
                val playlistId = recommendations.getJSONObject(i).getInt("playlist_id")
                val activityId = recommendations.getJSONObject(i).getInt("activity_id")
                Log.i("recommendations", "$activityId : $text")
                val elementButton = ((view.getChildAt(1) as LinearLayout).getChildAt(1) as Button)
                if (playlistId == -1) {
                    elementButton.text = "CREATE SPOTIFY PLAYLIST"
                    elementButton.setBackgroundResource(R.drawable.homepagebubbles_blue)
                    elementButton.setOnClickListener {
                        ApiUtil.createPlaylist(this, activityId.toInt())
                        elementButton.setBackgroundResource(R.drawable.homepagebubbles)
                        elementButton.text = "OPEN WITH SPOTIFY"
                        elementButton.setOnClickListener {
                            // TODO do this
                        }
                    }
                } else {
                    elementButton.text = "OPEN WITH SPOTIFY"
                    elementButton.setOnClickListener {
                        // TODO do this
                    }
                }

                val recommendationsList = findViewById<LinearLayout>(R.id.recommendations_list_layout)
                recommendationsList.addView(view)
            }
        }
    }
}