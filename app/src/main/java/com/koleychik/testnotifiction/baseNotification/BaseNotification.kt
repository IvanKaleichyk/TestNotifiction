package com.koleychik.testnotifiction.baseNotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.koleychik.testnotifiction.R

class BaseNotification(
    private val context: Context,
    private val channelId: String,
    private val intent: PendingIntent? = null
) {

    fun putNotification(
        manager: NotificationManager,
        notificationId: Int,
        title: String,
        text: String
    ) {
        val notification = createNotification(manager, title, text)
        if (notification != null) manager.notify(notificationId, notification)
    }

    fun createNotification(
        notificationManager: NotificationManager,
        title: String,
        text: String,
        icon: Int = R.mipmap.ic_launcher
    ): Notification? {
        val builder = getNotificationBuilder(notificationManager) ?: return null
        return builder
            .setContentIntent(intent)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(Notification.PRIORITY_MAX)
            .setDefaults(Notification.DEFAULT_ALL)
            .build()

    }

    private fun checkIfNotificationIsEnabled(notificationManager: NotificationManager): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//          check if notification's channel is enabled
            val channel =
                notificationManager.getNotificationChannel(channelId) ?: return false
            if (channel.importance == NotificationManager.IMPORTANCE_NONE) return false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val channelGroup = channel.group?.let {
                    notificationManager.getNotificationChannelGroup(channelId)
                }
                if (channelGroup != null && channelGroup.isBlocked) return false
            }
            return true
        }
        return true
    }

    private fun getNotificationBuilder(notificationManager: NotificationManager): Notification.Builder? {
        return if (!checkIfNotificationIsEnabled(notificationManager)) null
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) Notification.Builder(
            context,
            channelId
        )
        else Notification.Builder(context)
    }

    fun createNotificationManager(): NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(): NotificationChannel {
        return NotificationChannel(
            channelId,
            "channel 1 name",
            NotificationManager.IMPORTANCE_HIGH
        )

    }

}