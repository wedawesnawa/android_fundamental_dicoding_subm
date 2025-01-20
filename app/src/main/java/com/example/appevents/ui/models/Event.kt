package com.example.appevents.ui.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "event")
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val imageLogo: String,
    val name: String,
    val ownerName: String,
    val beginTime: String,
    val quota: Int,
    val registrants: Int,
    val description: String,
    val link: String,
    var isFavorite: Boolean = false
) : Parcelable