package com.example.queuecumber.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.queuecumber.LoginActivity
import com.example.queuecumber.R
import org.json.JSONObject

object ApiUtil {
    // TODO make this handle token refreshes

    /**
     * Get user info as JSON. See the
     * [API documentation](https://developer.spotify.com/documentation/web-api/reference/get-current-users-profile)
     * for details
     *
     * @param context `this` when calling from an activity class
     * @param responseListener Callback for successful request
     * @param errorListener Callback for unsuccessful request
     */
    fun userInfoRequest(
        context: AppCompatActivity,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ) {
        val tokensPref = context.getSharedPreferences(
            context.getString(R.string.tokens_file_key), Context.MODE_PRIVATE
        )
        val queue = Volley.newRequestQueue(context)
        val url = context.getString(R.string.domain) + context.getString(R.string.user_route)

        val userInfoRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            responseListener,
            errorListener) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers: HashMap<String, String> = HashMap()
                headers["Authorization"] = String.format(
                    "Bearer %s",
                    tokensPref.getString(context.getString(R.string.client_access_token), "")
                )
                return headers
            }
        }

        queue.add(userInfoRequest)
    }

    /**
     * Get user info as JSON. See the
     * [API documentation](https://developer.spotify.com/documentation/web-api/reference/get-current-users-profile)
     * for details
     *
     * @param context `this` when calling from an activity class
     * @param responseListener Callback for successful request
     */
    fun userInfoRequest(context: AppCompatActivity, responseListener: Response.Listener<JSONObject>) {
        userInfoRequest(context, responseListener
        ) { error ->
            Log.e("Homepage", error.toString())
            error.message?.let { Log.e("Homepage", it) }
        }
    }

    fun exchangeCodeForTokens(context: AppCompatActivity, data: Uri) {
        val authCode: String? = data.getQueryParameter("code")

        val queue = Volley.newRequestQueue(context)
        val url = context.getString(R.string.domain) + context.getString(R.string.callback_route) + "?code=$authCode"

        val clientTokenRequest = JsonObjectRequest(
            Request.Method.POST, url, null,
            { response ->
                val clientAccessToken = response.getString("clientAccessToken")
                val clientRefreshToken = response.getString("clientRefreshToken")

                val tokensPref = context.getSharedPreferences(
                    context.getString(R.string.tokens_file_key), Context.MODE_PRIVATE
                )
                with(tokensPref.edit()) {
                    putString(context.getString(R.string.client_access_token), clientAccessToken)
                    putString(context.getString(R.string.client_refresh_token), clientRefreshToken)
                    apply()
                }
            }, {error ->
                error.message?.let { Log.e("LoggingIn", it) }
                val loginIntent = Intent(context, LoginActivity::class.java)
                context.startActivity(loginIntent)
            }
        )
        queue.add(clientTokenRequest)
    }
}