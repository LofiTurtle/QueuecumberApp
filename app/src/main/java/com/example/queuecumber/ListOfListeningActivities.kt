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

class ListOfListeningActivities : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_listening_activities)

        val backButtonLA = findViewById<ImageButton>(R.id.back_button_activity_selection)
        backButtonLA.setOnClickListener {
            finish()
        }

        val extras = intent.extras
        var sessionId: Int = -1
        if (extras != null) {
            sessionId = extras.getInt("id")
        }

        ApiUtil.activitiesRequest(this) { response ->
            val activities = response.getJSONArray("activities")
            for (i in 0 until activities.length()) {
                Log.i("Activity list", i.toString() + " : " + activities.getString(i))
                val activityName = activities.getJSONObject(i).getString("name")
                val activityId = activities.getJSONObject(i).getInt("id")
                val view: LinearLayout =
                    LayoutInflater.from(this).inflate(R.layout.activities_list_element, null) as LinearLayout
                (view.getChildAt(0) as Button).text = activityName
                val activitiesList = findViewById<LinearLayout>(R.id.activities_list_layout)
                activitiesList.addView(view)

                (view.getChildAt(0) as Button).setOnClickListener {
                    ApiUtil.setSessionActivity(this, sessionId, activityId)
                }
            }
        }
    }
}