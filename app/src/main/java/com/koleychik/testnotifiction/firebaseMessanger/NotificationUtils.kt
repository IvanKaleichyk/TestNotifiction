package com.koleychik.testnotifiction.firebaseMessanger

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationUtils(private val topic: String) {

    companion object {
        const val TAG = "MAIN_APP_TAG"
    }

    fun sendNotification(title: String, text: String) =
        CoroutineScope(Dispatchers.IO).launch {

            val notification = PushNotification(
                NotificationData(title, text),
                topic
            )

            try {
                val response = RetrofitInstance.api.postNotification(notification)
                if (response.isSuccessful) {
                    Log.d(TAG, "Response: ${Gson().toJson(response)}")
                } else {
                    Log.e(TAG, response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }

}