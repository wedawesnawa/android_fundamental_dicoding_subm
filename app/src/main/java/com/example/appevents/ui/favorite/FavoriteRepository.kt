//package com.example.appevents.ui.favorite
//
//import androidx.lifecycle.LiveData
//import com.example.appevents.ui.database.EventDao
//import com.example.appevents.ui.models.Event
//
//class FavoriteRepository(private val eventDao: EventDao) {
//
//    suspend fun insert(event: Event) {
//        eventDao.addFavorite(event)
//    }
//
//    suspend fun delete(event: Event) {
//        eventDao.removeFavorite(event)
//    }
//
//    fun getAllFavorites(): LiveData<List<Event>> {
//        return eventDao.getFavoriteEvents()
//    }
//}