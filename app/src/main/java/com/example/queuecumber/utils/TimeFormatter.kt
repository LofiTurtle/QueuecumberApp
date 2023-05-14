package com.example.queuecumber.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeFormatter {
    fun formatDateTime(timeMillis: Long): String {
        val playedAtDate = Date(timeMillis)
        val sdf = SimpleDateFormat("MMM d, yyyy - h:mm a", Locale.getDefault())
        return sdf.format(playedAtDate)
    }

    fun formatDate(timeMillis: Long): String {
        val playedAtDate = Date(timeMillis)
        val sdf = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
        return sdf.format(playedAtDate)
    }

    fun formatTime(timeMillis: Long): String {
        val playedAtDate = Date(timeMillis)
        val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
        return sdf.format(playedAtDate)
    }
}