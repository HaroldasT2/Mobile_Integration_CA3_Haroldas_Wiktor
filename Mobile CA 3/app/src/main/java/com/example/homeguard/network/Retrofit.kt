package com.example.homeguard.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Data model for the Notifications
data class Notification(
    val id: Int,
    val title: String,
    val message: String
)

// Retrofit API interface
interface NotificationsApi {
    @GET("ab0f44d7-9777-43df-b76a-5bf55ac72a64")
    suspend fun getNotifications(): List<Notification>
}

// Singleton object for Retrofit
object RetrofitInstance {
    private const val BASE_URL = "https://run.mocky.io/v3/"

    val api: NotificationsApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NotificationsApi::class.java)
    }
}
