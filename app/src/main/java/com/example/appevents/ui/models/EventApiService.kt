package com.example.appevents.ui.models

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class EventResponse(
    val error: Boolean,
    val message: String,
    val listEvents: List<Event>
)

interface EventApiService {
    @GET("events")
    fun getFinishedEvents(
        @Query("active") active: Int = 0
    ): Call<EventResponse>
    @GET("events")
    fun getUpcomingEvents(
        @Query("active") active: Int = 1
    ): Call<EventResponse>
    @GET("events")
    fun getEvents(
        @Query("active") active: Int,
        @Query("limit") limit: Int = 5
    ): Call<EventResponse>
    @GET("events")
    fun searchEvents(
        @Query("q") query: String
    ): Call<EventResponse>
}