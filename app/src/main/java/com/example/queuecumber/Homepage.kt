package com.example.queuecumber

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.queuecumber.utils.ApiUtil


class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val toActivitiesButton = findViewById<Button>(R.id.homepage_to_activities)
        toActivitiesButton.setOnClickListener {
            val intent = Intent(this, ActivityHomePage::class.java)
            startActivity(intent)
        }

        val welcomeText = findViewById<TextView>(R.id.welcomeText)
        ApiUtil.userInfoRequest(this, { response ->
            welcomeText.text = "Welcome, " + response.getString("display_name") + "!"
        }, { error ->
            android.util.Log.e("Homepage", error.toString())
            error.message?.let { android.util.Log.e("Homepage", it) }
            welcomeText.text = "Couldn't get information"
        })
    }

    override fun onResume() {
        super.onResume()
        super.onNewIntent(intent)
        val data = intent.data
        if (data != null) {
            ApiUtil.exchangeCodeForTokens(this, data)
        }
    }
}