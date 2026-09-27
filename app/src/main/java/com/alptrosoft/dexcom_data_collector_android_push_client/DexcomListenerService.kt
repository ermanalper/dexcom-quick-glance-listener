package com.alptrosoft.dexcom_data_collector_android_push_client

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import com.alptrosoft.dexcom_data_collector_android_push_client.BuildConfig

class DexcomListenerService : NotificationListenerService() {

    private val client = OkHttpClient()
    private val scope = CoroutineScope(Dispatchers.IO)
    private val backendUrl = BuildConfig.ENDPOINT_URL;
    private val apiKey = BuildConfig.API_KEY;
    private var lastSentValue = -1
    private var lastSentTimeMs = 0L

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val packageName = sbn.packageName
        Log.d("DexcomPush", "Notification Caught: $packageName")

        //filter dexcom notifications
        if (!packageName.contains("dexcom", ignoreCase = true)) return

        val extras = sbn.notification.extras
        val title = extras.getString(Notification.EXTRA_TITLE) ?: ""
        val text = extras.getString(Notification.EXTRA_TEXT) ?: ""

        Log.d("DexcomPush", "TITLE: '$title' | Details: '$text'")

        val combinedText = "$title $text"

        val regex = Regex("(?:\\s|^)(\\d{2,3})\\s+([a-zA-Z\\s]+)")
        val match = regex.find(combinedText)

        if (match != null) {
            val glucoseValue = match.groupValues[1].toInt()
            val trendSymbol = match.groupValues[2].trim() // "Steady" vb.
            val currentTime = System.currentTimeMillis()

            Log.d("DexcomPush", "Successful Match: Value=$glucoseValue, Trend=$trendSymbol")

            // Spam protection (because the same notification will be logged multiple times)
            if (glucoseValue == lastSentValue && (currentTime - lastSentTimeMs) < 240_000) {
                Log.d("DexcomPush", "Spam protection, this value has already been sent: $glucoseValue")
                return
            }

            lastSentValue = glucoseValue
            lastSentTimeMs = currentTime

            sendToBackend(glucoseValue, trendSymbol, currentTime)
        } else {
            Log.d("DexcomPush", "Unmatched regex: $combinedText")
        }
    }

    private fun sendToBackend(value: Int, trend: String, timestampMs: Long) {
        scope.launch {
            try {
                val json = JSONObject().apply {
                    put("value", value)
                    put("trend_symbol", trend)
                    put("timestamp_ms", timestampMs)
                }

                val body = json.toString().toRequestBody("application/json; charset=utf-8".toMediaType())

                val request = Request.Builder()
                    .url(backendUrl)
                    .post(body)
                    .addHeader("X-API-Key", apiKey)
                    .build()

                client.newCall(request).execute().use { response ->
                    Log.d("DexcomPush", "Backend response: ${response.code}")
                }
            } catch (e: Exception) {
                Log.e("DexcomPush", "Error sending data", e)
            }
        }
    }
}