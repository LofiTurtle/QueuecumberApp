package com.example.queuecumber

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.queuecumber.utils.ApiUtil


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        ApiUtil.userInfoRequest(this, {
            // request succeeded, meaning user is logged in, so go to homepage
            val intent = Intent(this, Homepage::class.java)
            startActivity(intent)
        }, {
            // request failed, meaning user is likely not logged in, so go to login screen

            // clear old tokens from SharedPreferences
            val tokensPref = getSharedPreferences(
                getString(R.string.tokens_file_key), Context.MODE_PRIVATE
            )
            with(tokensPref.edit()) {
                putString(getString(com.example.queuecumber.R.string.client_access_token), "")
                putString(getString(com.example.queuecumber.R.string.client_refresh_token), "")
                apply()
            }

            // switch to login screen
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })
    }
}