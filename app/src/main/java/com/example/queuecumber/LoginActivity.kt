package com.example.queuecumber

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.Builder

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.login)
        loginButton.setOnClickListener {
            val spotifyLoginUrl = getString(R.string.domain) + getString(R.string.login_route)

            val builder = Builder()
            val customTabsIntent: CustomTabsIntent = builder.build()

            customTabsIntent.launchUrl(this, Uri.parse(spotifyLoginUrl))
        }
    }
}