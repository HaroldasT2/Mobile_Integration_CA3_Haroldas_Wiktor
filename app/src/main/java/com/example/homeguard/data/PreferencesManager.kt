package com.example.homeguard.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesManager(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private const val DATASTORE_NAME = "user_preferences"
        private val IS_BUTTON_GREEN = booleanPreferencesKey("is_button_green")
        private val IS_SLIDER_ON = booleanPreferencesKey("is_slider_on")

        private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)
    }

    val isButtonGreen: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_BUTTON_GREEN] ?: false
    }

    val isSliderOn: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_SLIDER_ON] ?: false
    }

    suspend fun saveButtonState(isGreen: Boolean) {
        Log.d("PreferencesManager", "Saving button state: $isGreen")
        dataStore.edit { preferences ->
            preferences[IS_BUTTON_GREEN] = isGreen
        }
    }

    suspend fun saveSliderState(isSliderOn: Boolean) {
        Log.d("PreferencesManager", "Saving slider state: $isSliderOn")
        dataStore.edit { preferences ->
            preferences[IS_SLIDER_ON] = isSliderOn
        }
    }
}