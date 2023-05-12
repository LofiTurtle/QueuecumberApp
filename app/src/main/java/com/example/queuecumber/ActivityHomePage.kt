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

        ApiUtil.activitiesRequest(this) { response ->
            Log.i("ActivityHomePage", "Made activitiesRequest() call successfully")
            val activities = response.getJSONArray("activities")
            for (i in 0 until activities.length()) {
                Log.i("Activity list", i.toString() + " : " + activities.getString(i))
                val view:LinearLayout =
                    LayoutInflater.from(this).inflate(R.layout.activities_list_element, null) as LinearLayout
                (view.getChildAt(0) as Button).text = activities.getString(i)
                val activitiesList = findViewById<LinearLayout>(R.id.activities_list_layout)
                activitiesList.addView(view)
            }
        }
    }
}