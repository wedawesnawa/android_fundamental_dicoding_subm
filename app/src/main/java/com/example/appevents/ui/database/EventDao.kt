package com.example.appevents.ui.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appevents.ui.models.Event

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(event: Event)

    @Delete
    suspend fun removeFavorite(event: Event)

    @Query("SELECT * FROM event WHERE id = :eventId LIMIT 1")
    suspend fun getEventById(eventId: Int): Event?

    @Query("SELECT * FROM event WHERE isFavorite = 1")
    fun getAllFavoriteEvents():  LiveData<List<Event>>

}
