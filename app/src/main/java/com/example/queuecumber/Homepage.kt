package com.example.queuecumber

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.queuecumber.utils.ApiUtil
import org.json.JSONArray
import org.json.JSONObject


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

        // showing off how to get the information for the homepage
        ApiUtil.homepageInfoRequest(this) { response ->
            // TODO do the homepage preview stuff
            val infoArray = ArrayList<JSONArray>()
            infoArray.add(response.getJSONArray("activities"))
            infoArray.add(response.getJSONArray("playlists"))
            infoArray.add(response.getJSONArray("history"))
            for (infoIndex in 0 until infoArray.size) {
                for (i in 0 until infoArray[infoIndex].length()) {
                    Log.i("Homepage Info", i.toString() + " " + infoArray[infoIndex].getString(i))
                }
            }
        }
    }
}