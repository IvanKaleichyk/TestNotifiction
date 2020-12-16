package com.koleychik.testnotifiction.firebaseMessanger

data class PushNotification(
    val data: NotificationData,
    val to: String
)