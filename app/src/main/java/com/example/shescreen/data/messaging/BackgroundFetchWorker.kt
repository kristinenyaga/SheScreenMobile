package com.example.shescreen.data.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.shescreen.R
import com.example.shescreen.data.api.DataRepository
import androidx.core.content.edit
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shescreen.data.api.DataViewModel
import com.example.shescreen.data.api.PrefsManager

class BackgroundFetchWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    val token = PrefsManager(context).getAuthToken("token")
    val patientId = PrefsManager(context).getPatientId("id")?.toInt()

    val followUpId = PrefsManager(context).getFollowId("followUpId")?.toInt()

    override fun doWork(): Result {
        Log.d("WorkManager", "✅ Worker triggered at ${System.currentTimeMillis()}")

        checkForNewRecommendations()
        checkForNewLabTests()
        checkForNewFollowUp()
        return Result.success()
    }

    private fun checkForNewRecommendations() {
        if (patientId == -1) {
            Log.e("WorkManager", "❌ Invalid patient ID")
            return
        }

        DataRepository.fetchRecommendation( patientId!!) { response ->
            if (response != null) {
                val prefs = applicationContext.getSharedPreferences("app_data", Context.MODE_PRIVATE)
                val oldJson = prefs.getString("last_recommendation", null)
                val newJson = response.toString()

                if (oldJson != newJson) {
                    prefs.edit { putString("last_recommendation", newJson) }
                    sendNotification("New Recommendation", "A new recommendation is available.")
                }
            }
        }
    }

    private fun checkForNewLabTests() {
        if (patientId == -1) {
            Log.e("WorkManager", "❌ Invalid patient ID")
            return
        }
        DataRepository.fetchLabTest(patientId!!) { response ->
            if (response != null) {
                val prefs = applicationContext.getSharedPreferences("app_data", Context.MODE_PRIVATE)
                val oldJson = prefs.getString("last_labtest", null)
                val newJson = response.toString()

                if (oldJson != newJson) {
                    prefs.edit { putString("last_labtest", newJson) }
                    sendNotification("New Lab Test", "A new lab test is available.")
                }
            }
        }
    }
    private fun checkForNewFollowUp() {
        if (followUpId == -1) {
            Log.e("WorkManager", "❌ Invalid patient ID")
            return
        }

        DataRepository.fetchFollowUp(followUpId!!) { response ->
            if (response != null) {
                val prefs = applicationContext.getSharedPreferences("app_data", Context.MODE_PRIVATE)
                val oldJson = prefs.getString("last_followup", null)
                val newJson = response.toString()

                if (oldJson != newJson) {
                    prefs.edit { putString("last_followup", newJson) }
                    sendNotification("New Follow Up", "A new follow up is available.")
                }
            }
        }
    }

    private fun sendNotification(title: String, message: String) {
        val channelId = "update_channel"
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, "Updates", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.prop)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .build()

        manager.notify(System.currentTimeMillis().toInt(), notification)
    }
}

