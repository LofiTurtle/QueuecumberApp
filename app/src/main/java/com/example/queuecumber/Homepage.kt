package com.example.queuecumber

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val welcomeText = findViewById<TextView>(R.id.welcomeText)
        val queue = Volley.newRequestQueue(this)
        val url = getString(R.string.domain) + getString(R.string.user_route)
        val tokensPref = this.getSharedPreferences(
            getString(R.string.tokens_file_key), Context.MODE_PRIVATE
        )

        val activitiesButton = findViewById<Button>(R.id.homepage_activities)
        activitiesButton.setOnClickListener {
            val intent = Intent(this, ActivityHomePage::class.java)
            startActivity(intent)
        }

        // TODO make a helper method for this
        val userInfoRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            {response ->
                welcomeText.setText("Welcome, " + response.getString("display_name") + "!")
            },
            {error ->
                Log.e("Homepage", error.toString())
                error.message?.let { Log.e("Homepage", it) }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers: HashMap<String, String> = HashMap()
                headers["Authorization"] = String.format(
                    "Bearer %s",
                    tokensPref.getString(getString(R.string.client_access_token), "")
                )
                return headers
            }
        }

        queue.add(userInfoRequest)
    }
}