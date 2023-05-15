package com.example.queuecumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import com.example.queuecumber.utils.ApiUtil

class ActivityHomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage_activities_section)

        val backButtonA = findViewById<ImageButton>(R.id.back_button_activities)
        backButtonA.setOnClickListener {
            finish()
        }

        val unlabeledSessionsButton = findViewById<Button>(R.id.unlabeled_sessions_button)
        unlabeledSessionsButton.setOnClickListener {
            val intent = Intent(this, UnlabeledListeningSessions::class.java)
            startActivity(intent)
        }

        val newActivityButton = findViewById<Button>(R.id.new_activity_button)
        newActivityButton.setOnClickListener {
            val intent = Intent(this, CreateNewUserActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        val activitiesList = findViewById<LinearLayout>(R.id.activities_list_layout)
        activitiesList.removeAllViews()

        ApiUtil.activitiesRequest(this) { response ->
            val activities = response.getJSONArray("activities")
            for (i in 0 until activities.length()) {
                Log.i("Activity list", i.toString() + " : " + activities.getString(i))
                val activityName = activities.getJSONObject(i).getString("name")
                val activityId = activities.getJSONObject(i).getInt("id")
                val view:LinearLayout =
                    LayoutInflater.from(this).inflate(R.layout.activities_list_element, null) as LinearLayout
                (view.getChildAt(0) as Button).text = activityName
                activitiesList.addView(view)
                (view.getChildAt(0) as Button).setOnClickListener {
                    val intent = Intent(this, ListeningSessionsPage::class.java)
                    intent.putExtra("activity_name", activityName)
                    intent.putExtra("activity_id", activityId)
                    startActivity(intent)
                }
            }
        }
    }
}