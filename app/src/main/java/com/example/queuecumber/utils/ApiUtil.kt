package com.example.queuecumber.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.contentValuesOf
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.ErrorListener
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.queuecumber.LoginActivity
import com.example.queuecumber.R
import org.json.JSONObject
import java.lang.Thread.sleep

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
        errorListener: ErrorListener
    ) {
        val queue = Volley.newRequestQueue(context)
        val url = context.getString(R.string.domain) + context.getString(R.string.user_route)

        val userInfoRequest = constructAuthorizedRequest(context, Request.Method.GET, url, responseListener, errorListener)

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
            Log.e("ApiUtil", error.toString())
            error.message?.let { Log.e("ApiUtil", it) }
        }
    }

    fun homepageInfoRequest(
        context: AppCompatActivity,
        responseListener: Response.Listener<JSONObject>
    ) {
        val queue = Volley.newRequestQueue(context)
        val url = context.getString(R.string.domain) + context.getString(R.string.homepage_info_route)
        val request = constructAuthorizedRequest(context, Request.Method.GET, url, responseListener)
        queue.add(request)
    }

    fun activitiesRequest(
        context: AppCompatActivity,
        responseListener: Response.Listener<JSONObject>
    ) {
        val queue = Volley.newRequestQueue(context)
        val url = context.getString(R.string.domain) + context.getString(R.string.activities_route)
        val request = constructAuthorizedRequest(context, Request.Method.GET, url, responseListener)
        queue.add(request)
    }

    fun sessionsRequest(
        context: AppCompatActivity,
        responseListener: Response.Listener<JSONObject>
    ) {
        val queue = Volley.newRequestQueue(context)
        val url = context.getString(R.string.domain) + context.getString(R.string.sessions_route)
        val request = constructAuthorizedRequest(context, Request.Method.GET, url, responseListener)
        queue.add(request)
    }

    fun activityPlaylistsRequest(
        context: AppCompatActivity,
        responseListener: Response.Listener<JSONObject>
    ) {
        val queue = Volley.newRequestQueue(context)
        val url = context.getString(R.string.domain) + context.getString(R.string.activity_playlists_route)
        val request = constructAuthorizedRequest(context, Request.Method.GET, url, responseListener)
        queue.add(request)
    }

    fun historyRequest(
        context: AppCompatActivity,
        responseListener: Response.Listener<JSONObject>
    ) {
        val url = context.getString(R.string.domain) + context.getString(R.string.history_route)
        val request = constructAuthorizedRequest(context, Request.Method.GET, url, responseListener)
        makeAuthorizedRequest(context, request)
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
                error.message?.let { Log.e("ApiUtil", it) }
                val loginIntent = Intent(context, LoginActivity::class.java)
                context.startActivity(loginIntent)
            }
        )
        queue.add(clientTokenRequest)
    }

    private fun constructAuthorizedRequest(
        context: AppCompatActivity,
        method: Int,
        url: String,
        responseListener: Response.Listener<JSONObject>,
        errorListener: ErrorListener
    ): JsonObjectRequest {
        return object : JsonObjectRequest(
            method, url, null,
            responseListener,
            errorListener) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers: HashMap<String, String> = HashMap()
                val tokensPref = context.getSharedPreferences(
                    context.getString(R.string.tokens_file_key), Context.MODE_PRIVATE
                )
                var maxWait = 10  // the max number of iterations to wait for tokens
                var CAT: String = tokensPref.getString(context.getString(R.string.client_access_token), "").toString()
                while (CAT == "" && maxWait > 0) {
                    // sometimes tokens are still being fetched.
                    // Wait and check periodically for them to be added
                    sleep(250)
                    CAT = tokensPref.getString(context.getString(R.string.client_access_token), "").toString()
                    maxWait--
                }
                headers["Authorization"] = String.format(
                    "Bearer %s",
                    tokensPref.getString(context.getString(R.string.client_access_token), "")
                )
                return headers
            }
        }
    }

    private fun constructAuthorizedRequest(
        context: AppCompatActivity,
        method: Int,
        url: String,
        responseListener: Response.Listener<JSONObject>
    ): JsonObjectRequest {
        return constructAuthorizedRequest(context, method, url, responseListener) { error ->
            Log.e("ApiUtil", error.toString())
            error.message?.let { Log.e("ApiUtil", it) }
        }
    }

    private fun makeAuthorizedRequest(
        context: AppCompatActivity,
        request: JsonObjectRequest
    ) {
        val queue = Volley.newRequestQueue(context)
        queue.add(request)
    }
}