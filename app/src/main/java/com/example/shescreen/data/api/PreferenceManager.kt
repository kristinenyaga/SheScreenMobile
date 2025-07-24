package com.example.shescreen.data.api

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PrefsManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("data_prefs", Context.MODE_PRIVATE)

    fun saveUserDetails(
        email: String,
        password: String
    ) {
        prefs.edit().apply {
            putString("email", email)
            putString("password", password)
            apply()
        }
    }

    fun saveAuthToken(
        accessToken: String,
    ) {
        prefs.edit().apply {
            putString("token", accessToken)
            apply()
        }
    }
    fun savePatientId(
        patientId: String,
    ) {
        prefs.edit().apply {
            putString("id", patientId)
            apply()
        }
    }
    fun saveFollowUpId(
        followUpId: String,
    ) {
        prefs.edit().apply {
            putString("followUpId", followUpId)
            apply()
        }
    }

    fun getUserDetail(key: String): String? = prefs.getString(key, null)

    fun getAuthToken(key: String): String? = prefs.getString(key, null)

    fun getPatientId(key: String): String? = prefs.getString(key, null)

    fun getFollowId(key: String): String? = prefs.getString(key, null)

    fun clear() {
        prefs.edit() { clear() }
    }
}