package com.example.queuecumber

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.queuecumber.utils.ApiUtil


class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val welcomeText = findViewById<TextView>(R.id.welcomeText)
        ApiUtil.userInfoRequest(this) { response ->
            welcomeText.setText("Welcome, " + response.getString("display_name") + "!")
        }
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