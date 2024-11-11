package com.example.shoppingapp.retrofit

import com.example.shoppingapp.firebase.PushNotification
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationAPI {

    @Headers("Authorization: key=YOUR_SERVER_KEY", "Content-Type: application/json")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<Void>
}