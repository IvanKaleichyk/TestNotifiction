package com.koleychik.testnotifiction.baseNotification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.koleychik.testnotifiction.MainActivity
import com.koleychik.testnotifiction.WorkWithJson
import java.lang.Exception

class ForegroundWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val channelId: String = inputData.getString(getChannelId) ?: ""
    private val title = inputData.getString(getTitle) ?: ""
    private val text = inputData.getString(getText) ?: ""
    private val manager: NotificationManager
    private val notificationId = 0

    private val baseNotification = BaseNotification(context, channelId, getPendingIntent())

    init {
        manager = baseNotification.createNotificationManager()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(baseNotification.createNotificationChannel())
        }
    }

    override suspend fun doWork(): Result {

        val notification = baseNotification.createNotification(
            notificationManager = manager,
            title = title,
            text = text
        )

        if (notification != null) {
            val foregroundInfo = ForegroundInfo(notificationId, notification)
            setForeground(foregroundInfo)
        }

        return Result.success()
    }

    private fun getPendingIntent(): PendingIntent? {
        return try {
            val workWithJson = WorkWithJson<PendingIntent>()
            val json = inputData.getString(getPendingIntentJson) ?: return null
            workWithJson.deserializeFromJson<PendingIntent>(json)
        } catch (e: Exception) {
            Log.d(MainActivity.TAG, "pendingIntent error")
            null
        }
    }

    companion object {
        const val getPendingIntent = "getPendingIntent"
        const val getPendingIntentJson = "getPendingIntentJson"
        const val getChannelId = "channelId"
        const val getTitle = "getTitle"
        const val getText = "getText"
    }

}