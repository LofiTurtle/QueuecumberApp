package com.example.queuecumber

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.queuecumber.utils.ApiUtil
import com.example.queuecumber.utils.TimeFormatter
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.ls.LSInput


class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = intent.data
        if (data != null) {
            ApiUtil.exchangeCodeForTokens(this, data)
        }

        setContentView(R.layout.activity_homepage)

        val toActivitiesButton = findViewById<LinearLayout>(R.id.homepage_to_activities)
        toActivitiesButton.setOnClickListener {
            val intent = Intent(this, ActivityHomePage::class.java)
            startActivity(intent)
        }

        val toRecommendationsButton = findViewById<LinearLayout>(R.id.homepage_to_recommendations)
        toRecommendationsButton.setOnClickListener {
            val intent = Intent(this, RecommendationsHomePage::class.java)
            startActivity(intent)
        }

        val toHistoryButton = findViewById<LinearLayout>(R.id.homepage_to_history)
        toHistoryButton.setOnClickListener {
            val intent = Intent(this, HistoryHomePage::class.java)
            startActivity(intent)
        }

        val welcomeText = findViewById<TextView>(R.id.welcomeText)
        ApiUtil.userInfoRequest(this, { response ->
            welcomeText.text = "Welcome, " + response.getString("display_name") + "!"
        }, { error ->
            Log.e("Homepage", error.toString())
            error.message?.let { android.util.Log.e("Homepage", it) }
            welcomeText.text = "Couldn't get information"
        })
    }

    override fun onResume() {
        super.onResume()

        val activitiesPreview = findViewById<LinearLayout>(R.id.homepage_to_activities)
        val recommendationsPreview = findViewById<LinearLayout>(R.id.homepage_to_recommendations)
        val historyPreview = findViewById<LinearLayout>(R.id.homepage_to_history)

        // TODO don't remove the first title view
        activitiesPreview.removeViews(1, activitiesPreview.childCount - 1)
        recommendationsPreview.removeViews(1, recommendationsPreview.childCount - 1)
        historyPreview.removeViews(1, historyPreview.childCount - 1)

        // showing off how to get the information for the homepage
        ApiUtil.homepageInfoRequest(this) { response ->
            val activities = response.getJSONArray("activities")
            for (i in 0 until activities.length()) {
                val activityName = activities.getJSONObject(i).getString("name")
                val view:LinearLayout =
                    LayoutInflater.from(this).inflate(R.layout.activities_preview_element, null) as LinearLayout
                (view.getChildAt(0) as TextView).text = activityName
                activitiesPreview.addView(view)
            }

            val recommendations = response.getJSONArray("playlists")
            for (i in 0 until recommendations.length()) {
                val playlistName = activities.getJSONObject(i).getString("name") + " Music"
                val view:LinearLayout =
                    LayoutInflater.from(this).inflate(R.layout.recommendations_preview_element, null) as LinearLayout
                (view.getChildAt(0) as TextView).text = playlistName
                recommendationsPreview.addView(view)
            }

            val history = response.getJSONArray("history")
            for (i in 0 until history.length()) {
                val view:LinearLayout =
                    LayoutInflater.from(this).inflate(R.layout.history_preview_element, null) as LinearLayout
                (view.getChildAt(1) as TextView).text = history.getJSONObject(i).getString("song_name")
                (view.getChildAt(2) as TextView).text = history.getJSONObject(i).getString("artist_name")
                val playedAtMillis = history.getJSONObject(i).getLong("played_at_millis")
                (view.getChildAt(3) as TextView).text = TimeFormatter.formatDateNoYear(playedAtMillis)
                historyPreview.addView(view)
            }
        }
    }
}