//package com.example.appevents.ui.favorite
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import com.example.appevents.ui.database.EventDatabase
//import com.example.appevents.ui.models.Event
//
//class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val eventDao = EventDatabase.getDatabase(application).eventDao()
//
//    val favoriteEvents: LiveData<List<Event>> = eventDao.getFavoriteEvents()
//}
