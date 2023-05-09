package com.example.queuecumber

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.queuecumber.utils.ApiUtil


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        ApiUtil.userInfoRequest(this, {res ->
            // request succeeded, meaning user is logged in, so go to homepage
            val intent = Intent(this, Homepage::class.java)
            startActivity(intent)
        }, {err ->
            // request failed, meaning user is likely not logged in, so go to login screen
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })
    }
}