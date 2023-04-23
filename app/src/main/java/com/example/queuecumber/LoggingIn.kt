package com.example.queuecumber

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class LoggingIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logging_in)
    }

    override fun onResume() {
        super.onResume()
        super.onNewIntent(intent)
        val data = intent.data
        if (data != null) {
            val authCode: String? = data.getQueryParameter("code")
            println("code=$authCode")

            val queue = Volley.newRequestQueue(this)
            val url = getString(R.string.domain) + getString(R.string.callback_route) + "?code=$authCode"

            val clientTokenRequest = JsonObjectRequest(
                Request.Method.POST, url, null,
                { response ->
                    val clientAccessToken = response.getString("clientAccessToken")
                    val clientRefreshToken = response.getString("clientRefreshToken")

                    val tokensPref = this.getSharedPreferences(
                        getString(R.string.tokens_file_key), Context.MODE_PRIVATE
                    )
                    with(tokensPref.edit()) {
                        putString(getString(R.string.client_access_token), clientAccessToken)
                        putString(getString(R.string.client_refresh_token), clientRefreshToken)
                        apply()
                    }

                    val homepageIntent = Intent(this, Homepage::class.java)
                    startActivity(homepageIntent)
                }, {error ->
                    error.message?.let { Log.e("LoggingIn", it) }
                    val loginIntent = Intent(this, LoginActivity::class.java)
                    startActivity(loginIntent)
                }
            )
            queue.add(clientTokenRequest)
        }
    }
}