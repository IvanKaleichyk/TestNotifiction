package com.koleychik.testnotifiction.firebaseMessanger

import com.koleychik.testnotifiction.firebaseMessanger.Constants.Companion.CONTENT_TYPE
import com.koleychik.testnotifiction.firebaseMessanger.Constants.Companion.TOKEN
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationAPI {

    @Headers("Authorization: key=$TOKEN", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}