package com.example.appevents.ui.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

object ThemePreferences {
    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    private val DAILY_REMINDER_KEY = booleanPreferencesKey("daily_reminder")

    suspend fun setDarkMode(context: Context, isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isDarkMode
        }
    }

    fun isDarkMode(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[DARK_MODE_KEY] ?: false
        }
    }

    suspend fun setDailyReminder(context: Context, isReminderOn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DAILY_REMINDER_KEY] = isReminderOn
        }
    }

    fun isDailyReminderOn(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[DAILY_REMINDER_KEY] ?: false
        }
    }
}